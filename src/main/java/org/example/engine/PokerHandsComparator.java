package org.example.engine;

import org.example.entity.*;

import java.util.*;
import java.util.stream.Collectors;

public class PokerHandsComparator implements HandsComparator {
    @Override
    public GameResult compareHands(Card[] blackHand, Card[] whiteHand) {
        Rank blackHandRank = calculateRank(blackHand);
        Rank whiteHandRank = calculateRank(whiteHand);

        return getGameResultByRank(whiteHandRank.rank, blackHandRank.rank);
    }

    public GameResult compareHandsIfRanksAreSame(Rank rank, Card[] whiteHand, Card[] blackHand) {
        if (rank.equals(Rank.Straight_flush)) {
            //Ranked by the highest card in the hand.
            return getResultSameHandsStraightFlush(whiteHand, blackHand);
        }
        if (rank.equals(Rank.Four_of_a_kind)) {
            // Ranked by the value of the 4 cards.
            return getResultSameFourOfKind(whiteHand, blackHand);
        }
        if(rank.equals(Rank.Full_House)){
            // Ranked by the value of the 3 cards.
            return getResultSameFullHouse(whiteHand, blackHand);
        }

        return new GameResult(ResultOption.T, null);
    }

    public GameResult getResultSameFullHouse(Card[] whiteHand, Card[] blackHand) {
        int whiteHandRank = Integer.parseInt(getRankOfTheMostFrequentCard(whiteHand, 3));
        int blackHandRank = Integer.parseInt(getRankOfTheMostFrequentCard(blackHand, 3));
        return getGameResultByRank(whiteHandRank, blackHandRank);
    }

    public GameResult getResultSameFourOfKind(Card[] whiteHand, Card[] blackHand) {
        int whiteHandRank = Integer.parseInt(getRankOfTheMostFrequentCard(whiteHand, 4));
        int blackHandRank = Integer.parseInt(getRankOfTheMostFrequentCard(blackHand, 4));
        return getGameResultByRank(whiteHandRank, blackHandRank);
    }

    private static GameResult getGameResultByRank(int whiteHandRank, int blackHandRank) {
        if (whiteHandRank > blackHandRank) {
            return new GameResult(ResultOption.W, Player.White);
        } else if (whiteHandRank < blackHandRank) {
            return new GameResult(ResultOption.W, Player.Black);
        }
        return new GameResult(ResultOption.T, null);
    }

    private static String getRankOfTheMostFrequentCard(Card[] cards, int frequency) {
        Optional<Map.Entry<String, Integer>> first = getValueFrequency(cards)
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == frequency)
                .findFirst();
       if(first.isPresent()){
           return first.get().getKey();
       } else{
           throw new IllegalArgumentException("there is not four of kind case");
       }
    }


    public GameResult getResultSameHandsStraightFlush(Card[] whiteHand, Card[] blackHand) {
        if (getHighCard(whiteHand).compareTo(getHighCard(blackHand)) > 0) {
            return new GameResult(ResultOption.W, Player.White);
        } else if (getHighCard(whiteHand).compareTo(getHighCard(blackHand)) < 0) {
            return new GameResult(ResultOption.W, Player.Black);
        } else return new GameResult(ResultOption.T, null);
    }

    private Rank calculateRank(Card[] hand) {
        if (hand.length != 5) {
            throw new IllegalArgumentException();
        }
        if (isStraightFlush(hand)) {
            return Rank.Straight_flush;
        }
        if (isFourOfAKind(hand)) {
            return Rank.Four_of_a_kind;
        }
        if (isFullHouse(hand)) {
            return Rank.Full_House;
        }
        if (isFlush(hand)) {
            return Rank.Flush;
        }
        if (isStraight(hand)) {
            return Rank.Straight;
        }
        if (isThreeOfKind(hand)) {
            return Rank.Three_of_a_Kind;
        }
        if (isTwoPairs(hand)) {
            return Rank.Two_Pairs;
        }
        if (isOnePairPresent(hand)) {
            return Rank.Pair;
        }
        return Rank.High_Card;
    }


    public boolean isStraightFlush(Card[] hand) {
        Club firstClub = hand[0].getClub();
        if (!(Arrays.stream(hand).allMatch(h -> h.getClub() == firstClub))) {
            return false;
        }
        return isStraight(hand);
    }

    public boolean isFourOfAKind(Card[] hand) {
        List<String> uniques = getDistinctValueCards(hand);
        if (uniques.size() != 2) {
            return false;
        } else {
            return Arrays.stream(hand).filter(c -> c.getValue().symbol == uniques.get(0)).count() == 4 ||
                    Arrays.stream(hand).filter(c -> c.getValue().symbol == uniques.get(1)).count() == 4;
        }

    }

    public boolean isFullHouse(Card[] hand) {
        //3 cards of the same value, with the remaining 2 cards forming a pair.
        // Ranked by the value of the 3 cards.
        HashMap<String, Integer> frequency = getValueFrequency(hand);
        return frequency.size() == 2;
    }

    private static HashMap<String, Integer> getValueFrequency(Card[] hand) {
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

    // Hand contains 5 cards of the same suit. Hands which are both flushes are ranked using the rules for High Card.
    public boolean isFlush(Card[] hand) {
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

    public boolean isStraight(Card[] hand) {
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

    public boolean isThreeOfKind(Card[] hand) {
        //Three of the cards in the hand have the same value.
        return getValueFrequency(hand).containsValue(3);
    }

    public boolean isTwoPairs(Card[] hand) {
        //The hand contains 2 different pairs.
        return getPairsSetValues(hand).size() == 2;

    }

    public boolean isOnePairPresent(Card[] hand) {
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


    public Card getHighCard(Card[] hand) {
        return Arrays.stream(hand).max(Card::compareTo).get();
    }

}
