package me.abdullah.elolib;

import javafx.util.Pair;

public interface IEloComparator {

    Pair<Integer, Integer> computeChange(PlayerResult p1, PlayerResult p2);
}
