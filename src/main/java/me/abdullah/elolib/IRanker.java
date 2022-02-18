package me.abdullah.elolib;

/***
 * Interface to implement different elo to rank sorting methods
 */
public interface IRanker<T> {

    /***
     * Finds the corresponding rank for the given elo
     * @param elo Elo to search for
     * @return The given elo's corresponding rank
     */
    T getRank(int elo);
}
