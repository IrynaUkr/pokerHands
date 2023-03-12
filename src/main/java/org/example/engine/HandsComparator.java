package org.example.engine;


import org.example.entity.Card;
import org.example.entity.GameResult;

public interface HandsComparator {
    GameResult compareHands(Card[] blackHand, Card[] whiteHand);

}
