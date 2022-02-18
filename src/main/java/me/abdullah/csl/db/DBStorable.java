package me.abdullah.csl.db;

import com.mongodb.DBObject;

/***
 * Interface to implement class to DBObject conversion
 */
public interface DBStorable {

    /***
     * Creates a DBObject with the class' information
     * @return A DBObject with the class' information
     */
    DBObject getAsDBObject();
}
