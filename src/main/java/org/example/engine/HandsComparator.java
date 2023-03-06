package org.example.engine;


import org.example.entity.*;

public interface HandsComparator {
    public GameResult compareHands(Card[] blackHand, Card[] whiteHand);
}
