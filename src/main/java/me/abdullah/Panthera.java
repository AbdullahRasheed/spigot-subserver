package me.abdullah;

import me.abdullah.concurrent.ExecutorThreads;
import me.abdullah.csl.db.CacheRoutine;
import me.abdullah.csl.db.Caches;
import me.abdullah.csl.db.DBCache;
import me.abdullah.subserver.Subserver;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.UnknownHostException;

public class Panthera extends JavaPlugin {

    private static Panthera INSTANCE;

    private Subserver subserver;

    public void onEnable(){
        try {
            Caches.initialize();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            subserver = new Subserver(6969);
            subserver.begin();
        } catch (IOException e) {
            Bukkit.getLogger().severe("SUBSERVER DID NOT START!! CLOSING SERVER");
        }

        INSTANCE = this;
    }

    public void onDisable(){
        // store db to cache
        Caches.getClient().close();
        subserver.close();
    }

    public Subserver getSubserver(){
        return subserver;
    }

    public static Panthera getPanthera(){
        return INSTANCE;
    }

}
