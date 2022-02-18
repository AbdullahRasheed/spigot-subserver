package me.abdullah.csl.db;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

/***
 * Collects DBCaches to store back to Mongo on a scheduled service
 */
@Configuration
@EnableScheduling
public class CacheRoutine implements SchedulingConfigurer {

    // Cache routine future
    private ScheduledFuture<?> cacheRoutine;

    // Pool of caches to write
    private static final List<DBCache<?, ? extends DBStorable>> caches = new ArrayList<>();

    /***
     * Begins the caching routine
     */
    public static void begin(){
        SpringApplication.run(CacheRoutine.class);
    }

    /***
     * Adds the given cache to the writing scheduler
     * @param cache Cache to schedule
     */
    public static synchronized void addCache(DBCache<?, ? extends DBStorable> cache){
        caches.add(cache);
    }

    /***
     * Processes each cache with the given consumer
     * @param consumer Consumer to process with
     */
    private static synchronized void processCaches(Consumer<DBCache<?, ? extends DBStorable>> consumer){
        for (DBCache<?, ? extends DBStorable> cache : caches) {
            consumer.accept(cache);
        }
    }

    /***
     * Configures scheduling pools
     * @param scheduledTaskRegistrar The given task registrar to register schedulers
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.initialize();

        runScheduler(taskScheduler);

        scheduledTaskRegistrar.setTaskScheduler(taskScheduler);
    }

    /***
     * Starts the cache storing routine for every 10 minutes
     * @param taskScheduler Task scheduler to schedule on
     */
    private void runScheduler(TaskScheduler taskScheduler){
        cacheRoutine = taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                processCaches(DBCache::storeCache);
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger("* 0/10 * * * *").nextExecutionTime(triggerContext);
            }
        });
    }

    /***
     * Kills the scheduler service
     * @param interrupt Whether to interrupt if a task is running
     */
    public void kill(boolean interrupt){
        cacheRoutine.cancel(interrupt); // boolean try if or isDone/isCancelled to run something after
    }
}
