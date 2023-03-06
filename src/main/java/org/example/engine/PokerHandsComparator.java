package org.example.engine;

import org.example.entity.*;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PokerHandsComparator implements HandsComparator {
    @Override
    public GameResult compareHands(Card[] blackHand, Card[] whiteHand) {
        Rank blackHandRank = calculateRank(blackHand);
        Rank whiteHandRank = calculateRank(whiteHand);

        if (whiteHandRank.rank > blackHandRank.rank) {
            return new GameResult(ResultOption.W, Player.White);
        } else if (whiteHandRank.rank < blackHandRank.rank) {
            return new GameResult(ResultOption.W, Player.Black);
        }

        return new GameResult(ResultOption.T, null);
    }

    private Rank calculateRank(Card[] hand) {
        if (hand.length != 5) {
            throw new IllegalArgumentException();
        }
        if (isStraightFlush(hand)) {
            return Rank.Straight_flush;
        }

        return Rank.High_Card;
    }



    public boolean isStraightFlush(Card[] hand) {
        Club firstClub = hand[0].getClub();
        if (!(Arrays.stream(hand).allMatch(h -> h.getClub() == firstClub))) {
            return false;
        }
        List<Integer> sorted = Arrays.stream(hand)
                .map(card -> card.getValue().order)
                .sorted()
                .collect(Collectors.toList());
        for (int i = 0; i < sorted.size() - 2; i++) {
            if (sorted.get(i + 1) - sorted.get(i) != 1) {
                return false;
            }
        }
        return true;
    }

}
