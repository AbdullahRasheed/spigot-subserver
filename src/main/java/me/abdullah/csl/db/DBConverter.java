package me.abdullah.csl.db;

import com.mongodb.DBObject;

/***
 * Interface used to convert DBObjects to objects of type T
 * @param <K> Query type
 * @param <V> Type to convert to
 */
public interface DBConverter<K, V> {

    /***
     * Method to convert the given DBObject to an object of type V
     * @param query Query that was searched for
     * @param obj DBObject to convert
     * @return Object of type V to return
     */
    V convert(K query, DBObject obj);
}
