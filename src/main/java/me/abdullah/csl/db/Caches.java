package me.abdullah.csl.db;

import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.UUID;

public class Caches {

    private static MongoClient client;
    private static DBCache<UUID, DBPlayer> playerCache;

    public static void initialize() throws UnknownHostException {
        client = new MongoClient(); // localhost

        playerCache = new DBCache<>(client, "panthera", "players", ((query, obj) -> {
                    if(obj == null) return new DBPlayer(query);
                    return new DBPlayer(obj);
                }));

        CacheRoutine.addCache(playerCache);

        CacheRoutine.begin();
    }

    public static DBCache<UUID, DBPlayer> getPlayerCache(){
        return playerCache;
    }

    public static MongoClient getClient(){
        return client;
    }
}
