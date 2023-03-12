package org.example.entity;

public enum Rank {
    //Poker hands are ranked by the following partial order from lowest to highest.
    Straight_flush(9),
    Four_of_a_kind(8),
    Full_House(7),
    Flush(6),
    Straight(5),
    Three_of_a_Kind(4),
    Two_Pairs(3),
    Pair(2),
    High_Card(1);

    public final Integer rank;

    Rank(Integer rank) {
        this.rank = rank;
    }
}
