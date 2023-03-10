package org.example.engine;

import org.example.entity.Card;
import org.example.entity.Club;
import org.example.entity.Rank;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.entity.Rank.*;

public class RankCalculator {
    private RankCalculator() {
    }

    public static Rank calculateRank(Card[] hand) {
        if (hand.length != 5) {
            throw new IllegalArgumentException();
        }
        if (isStraightFlush(hand)) {
            return Straight_flush;
        }
        if (isFourOfAKind(hand)) {
            return Four_of_a_kind;
        }
        if (isFullHouse(hand)) {
            return Full_House;
        }
        if (isFlush(hand)) {
            return Flush;
        }
        if (isStraight(hand)) {
            return Straight;
        }
        if (isThreeOfKind(hand)) {
            return Three_of_a_Kind;
        }
        if (isTwoPairs(hand)) {
            return Two_Pairs;
        }
        if (isOnePair(hand)) {
            return Pair;
        }
        return High_Card;
    }

    public static boolean isStraightFlush(Card[] hand) {
        Club firstClub = hand[0].getClub();
        if (!(Arrays.stream(hand).allMatch(h -> h.getClub() == firstClub))) {
            return false;
        }
        return isStraight(hand);
    }

    public static boolean isFourOfAKind(Card[] hand) {
        List<String> uniques = getDistinctValueCards(hand);
        if (uniques.size() != 2) {
            return false;
        } else {
            return Arrays.stream(hand).filter(c -> Objects.equals(c.getValue().symbol, uniques.get(0))).count() == 4 ||
                    Arrays.stream(hand).filter(c -> Objects.equals(c.getValue().symbol, uniques.get(1))).count() == 4;
        }

    }

    public static boolean isFullHouse(Card[] hand) {
        //3 cards of the same value, with the remaining 2 cards forming a pair.
        // Ranked by the value of the 3 cards.
        HashMap<String, Integer> frequency = getValueFrequency(hand);
        return frequency.size() == 2;
    }

    public static boolean isFlush(Card[] hand) {
        HashMap<String, Integer> frequency = new HashMap<>();
        for (Card card : hand) {
            String suit = card.getClub().toString();
            if (frequency.containsKey(suit)) {
                Integer freq = frequency.get(suit);
                frequency.put(suit, freq + 1);
            } else {
                frequency.put(suit, 1);
            }
        }
        return frequency.size() == 1;
    }

    public static boolean isStraight(Card[] hand) {
        // Hand contains 5 cards with consecutive values.
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

    public static boolean isThreeOfKind(Card[] hand) {
        //Three of the cards in the hand have the same value.
        return getValueFrequency(hand).containsValue(3);
    }

    public static boolean isTwoPairs(Card[] hand) {
        //The hand contains 2 different pairs.
        return getPairsSetValues(hand).size() == 2;

    }

    public static boolean isOnePair(Card[] hand) {
        return getPairsSetValues(hand).size() == 1;
    }

    private static Set<String> getPairsSetValues(Card[] hand) {
        Set<String> pairs = new HashSet<>();
        HashMap<String, Integer> valueFrequency = getValueFrequency(hand);
        Set<Map.Entry<String, Integer>> entries = valueFrequency.entrySet();

        for (Map.Entry<String, Integer> entry : entries) {
            if (entry.getValue().equals(2)) {
                pairs.add(entry.getKey());
            }
        }
        return pairs;
    }

    private static List<String> getDistinctValueCards(Card[] hand) {
        return Arrays.stream(hand)
                .map(c -> c.getValue().symbol)
                .distinct()
                .collect(Collectors.toList());
    }

    static HashMap<String, Integer> getValueFrequency(Card[] hand) {
        HashMap<String, Integer> frequency = new HashMap<>();
        for (Card card : hand) {
            String symbol = card.getValue().symbol;
            if (frequency.containsKey(symbol)) {
                Integer freq = frequency.get(symbol);
                frequency.put(symbol, freq + 1);
            } else {
                frequency.put(symbol, 1);
            }
        }
        return frequency;
    }

}
