package org.example.entity;

public enum Rank {
    //Poker hands are ranked by the following partial order from lowest to highest.
    Straight_flush(1),
    Four_of_a_kind(2),
    Full_House(3),
    Flush(4),
    Straight(5),
    Three_of_a_Kind(6),
    Two_Pairs(7),
    Pair(8),
    High_Card(9);

    public final Integer rank;

    Rank(Integer rank) {
        this.rank = rank;
    }
}
