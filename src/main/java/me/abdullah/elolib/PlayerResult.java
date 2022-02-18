package me.abdullah.elolib;

/***
 * Stores info needed to compute elo changes for individual players
 */
public class PlayerResult {

    private final int elo, score, k;

    /***
     * Stores the given info about a player
     * @param elo The player's elo
     * @param score The score the player received
     * @param k The player's k-factor to use
     */
    public PlayerResult(int elo, int score, int k){
        this.elo = elo;
        this.score = score;
        this.k = k;
    }

    /***
     * Gets the player's elo
     * @return The player's elo
     */
    public int getElo(){
        return elo;
    }

    /***
     * Gets the player's score
     * @return The player's score
     */
    public int getScore(){
        return score;
    }

    /***
     * Gets the player's k-factor
     * @return The player's k-factor
     */
    public int getK(){
        return k;
    }
}
