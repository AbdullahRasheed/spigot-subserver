package me.abdullah.csl.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.UUID;

/***
 * Class consisting of DB information of the player
 */
public class DBPlayer implements DBStorable {

    /*
    player = {
        _id: "(uuid)"
        elo: (player's elo)
        kills: (total kills)
        deaths: (total deaths)
        wins: (total wins)
        losses: (total losses)
        draws: (total draws)
        games = [i_1, i_2, ..., i_10] (last 10 game ids)
    }
     */

    // The player's UUID
    private final String uuid;

    // The player's current elo rating
    public int elo;

    // The total number of kills the player made
    public int kills;

    // The total number of deaths the player has
    public int deaths;

    // The total number of wins the player has
    public int wins;

    // The total number of losses the player has
    public int losses;

    // The total number of draws the player has
    public int draws;

    // An array consisting of the last 10 game IDs the player played
    public long[] games;

    /***
     * Constructs the DBPlayer with the given DBObject
     * @param obj DBObject to read
     */
    public DBPlayer(DBObject obj){
        uuid = (String) obj.get("_id");
        elo = (int) obj.get("elo");
        kills = (int) obj.get("kills");
        deaths = (int) obj.get("deaths");
        wins = (int) obj.get("wins");
        losses = (int) obj.get("losses");
        draws = (int) obj.get("draws");
        games = (long[]) obj.get("games");
    }

    /***
     * Constructs a DBPlayer with default DB stats
     * @param uuid UUID of the user to create
     */
    public DBPlayer(UUID uuid){
        this.uuid = uuid.toString();
        elo = 1000;
        kills = 0;
        deaths = 0;
        wins = 0;
        losses = 0;
        draws = 0;
        games = new long[10];
    }

    /***
     * Gets the player's unique id as a UUID
     * @return The player's UUID
     */
    public UUID getUUID(){
        return UUID.fromString(uuid);
    }

    /***
     * Creates a DBObject with the player's information
     * @return A DBObject with the player's information
     */
    @Override
    public DBObject getAsDBObject() {
        return new BasicDBObject("_id", uuid)
                .append("elo", elo)
                .append("kills", kills)
                .append("deaths", deaths)
                .append("wins", wins)
                .append("losses", losses)
                .append("draws", draws)
                .append("games", games);
    }
}
