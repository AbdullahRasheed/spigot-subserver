package me.abdullah.elolib;

/***
 * Class used to separate elo ranges into ranks of any type
 * @param <T> Rank class type
 */
public class EloRanker<T> implements IRanker<T> {

    // Int array consisting of the lowest elo for each rank in order
    private int[] eloBounds;

    // Array consisting of the ranks corresponding to the minimum elos from eloBounds
    private T[] ranks;

    /***
     * Constructs the Elo Ranker of type T
     * @param eloBounds An int array consisting of the lowest elo for each rank in order
     * @param ranks An array of type T and same length of eloBounds consisting of the ranks corresponding to the minimum elos of eloBounds
     */
    public EloRanker(int[] eloBounds, T[] ranks){
        if(eloBounds.length != ranks.length)
            throw new IllegalArgumentException("Elo bounds array must be of the same length as the ranks array!");

        this.eloBounds = eloBounds;
        this.ranks = ranks;
    }

    /***
     * Finds the corresponding rank for the given elo
     * @param elo Elo to search for
     * @return The given elo's corresponding rank
     */
    public T getRank(int elo){
        return ranks[index(elo)];
    }

    /***
     * Gets the Elo Bounds that this Elo Ranker is working around
     * @return The given elo bounds
     */
    public int[] getEloBounds(){
        return eloBounds;
    }

    /***
     * Gets the ranks corresponding to the elo bounds
     * @return The ranks corresponding to the elo bounds
     */
    public T[] getRanks(){
        return ranks;
    }

    /***
     * Completes a binary search on eloBounds to find the minimum corresponding elo
     * @param n Elo to search for
     * @return The minimum corresponding elo's index
     */
    private int index(int n){
        if(eloBounds.length == 1) return 0;
        if(n >= eloBounds[eloBounds.length-1]) return eloBounds.length-1;
        if(n < eloBounds[0]) throw new IllegalArgumentException("Number is out of eloBounds");

        int lower = 0;
        int upper = eloBounds.length;
        int mid = upper/2;

        while(n >= eloBounds[mid + 1] || n < eloBounds[mid]){
            if(eloBounds[mid] == n) return mid;

            if(n < eloBounds[mid]){
                upper = mid - 1;
            }else{
                lower = mid + 1;
            }

            mid = lower + (upper - lower)/2;
        }

        return mid;
    }
}
