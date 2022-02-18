package me.abdullah.elolib;

import javafx.util.Pair;

/***
 * Class used to compare and compute elo using a standard FIDE elo system
 */
public class StandardEloComparator implements IEloComparator {

    // Scale to rank elo by
    private final int ratingScale;

    /***
     * Constructs the Elo Comparator using a standard FIDE system
     * @param ratingScale Scale to rank elo by (FIDE uses 400)
     */
    public StandardEloComparator(int ratingScale){
        this.ratingScale = ratingScale;
    }

    /***
     * Computes the new elo ratings for each player using a standard FIDE elo system
     * @param p1 The elo, score, and k-factor of player 1
     * @param p2 The elo, score, and k-factor of player 2
     * @return A pair consisting of first the elo change for player 1 (key) and the necessary elo change for player 2 (value)
     */
    public Pair<Integer, Integer> computeChange(PlayerResult p1, PlayerResult p2) {
        float e1 = expectedScore(p1.getElo(), p2.getElo()), e2 = 1 - e1;
        return new Pair<Integer, Integer>(Math.round(p1.getK()*(p1.getScore()-e1)),
                Math.round(p2.getK()*(p2.getScore()-e2)));
    }

    /***
     * Computes the expected win chance of the player corresponding to r1
     * @param r1 The rating of the player to evaluate
     * @param r2 The rating of the player they are comparing to
     * @return The expected win chance of the player corresponding to r1
     */
    private float expectedScore(int r1, int r2){
        return (float)(1/(1 + Math.pow(10, (double)(r1 - r2)/ratingScale)));
    }
}
