package me.abdullah.csl.db;

import com.mongodb.*;

import java.util.HashMap;
import java.util.Map;

/***
 * Cache for MongoDB objects and DBStorables
 * @param <K> Key of the DBObject (id type)
 * @param <V> DBStorable type to cache
 */
public class DBCache<K, V extends DBStorable> {

    // Collection to read and write to
    private final DBCollection collection;

    // Interface to convert a given DBObject to an object of type V
    private final DBConverter<K, V> converter;

    // Cache storing requested data
    private final Map<K, V> cache;

    /***
     * Uses the Mongo connection with the given DB and Collection
     * @param client Mongo connection
     * @param db Database name to use
     * @param collection Collection name to use
     */
    public DBCache(MongoClient client, String db, String collection, DBConverter<K, V> converter){
        this.collection = client.getDB(db).getCollection(collection);
        this.converter = converter;

        this.cache = new HashMap<>();
    }

    /***
     * Stores the given key value pair to the cache
     * @param key Key to use
     * @param value Value to store
     */
    public void store(K key, V value){
        cache.put(key, value);
    }

    /***
     * Gets the given key's associated value from the stored cache
     * @param key Key to look for
     * @return The associated value
     */
    public V get(K key){
        return cache.get(key);
    }

    /***
     * Retrieves the given key's corresponding DBObject from Mongo as an object of type V
     * @param key The key to search for
     * @return The given key's corresponding DBObject from Mongo as an object of type V
     */
    public V retrieve(K key){
        DBObject query = new BasicDBObject("_id", key);
        DBCursor cursor = collection.find(query);

        return converter.convert(key, cursor.one());
    }

    /***
     * Retrieves the given key's corresponding DBObject from Mongo as an object of type V
     * @param key The key to search for
     * @return The given key's corresponding DBObject from Mongo as an object of type V
     */
    public V retrieveAndStore(K key){
        V value = retrieve(key);
        store(key, value);

        return value;
    }

    /***
     * Stores the cache back to the collection
     */
    public void storeCache(){
        for (DBStorable value : cache.values()) {
            collection.save(value.getAsDBObject());
        }
    }

    /***
     * Gets the stored cache
     * @return The stored cache
     */
    public Map<K, V> getCache(){
        return cache;
    }

    /***
     * Gets the collection corresponding to this cache
     * @return The collection corresponding to this cache
     */
    public DBCollection getCollection(){
        return collection;
    }
}
