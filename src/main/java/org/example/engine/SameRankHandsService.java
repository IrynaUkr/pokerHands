package org.example.engine;

import org.example.entity.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.engine.PokerHandsComparator.getGameResultByRank;
import static org.example.engine.RankCalculator.getValueFrequency;
import static org.example.entity.Rank.*;

public class SameRankHandsService {
    private SameRankHandsService() {
    }

    public static GameResult getGameResultIfSameHandsRanks(Rank rank, Card[] whiteHand, Card[] blackHand) {
        if (rank.equals(Straight_flush)) {
            //Ranked by the highest card in the hand.
            return getResultSameHandsStraightFlush(whiteHand, blackHand);
        }
        if (rank.equals(Four_of_a_kind)) {
            // Ranked by the value of the 4 cards.
            return getResultSameFourOfKind(whiteHand, blackHand);
        }
        if (rank.equals(Full_House) || rank.equals(Three_of_a_Kind)) {
            // Ranked by the value of the 3 cards.
            return getResultSameFullHouseAndThreeOfAKind(whiteHand, blackHand);
        }
        if (rank.equals(Flush) || rank.equals(Straight)) {
            //Hands are ranked using the rules for High Card.
            return getResultHighHand(whiteHand, blackHand);
        }
        if (rank.equals(Two_Pairs)) {
            //ranked by the value of their highest pair.
            // Hands with the same highest pair are ranked by the value of their other pair.
            // If these values are the same the hands are ranked by the value of the remaining card.
            return getResultSameTwoPairs(whiteHand, blackHand);
        }
        if (rank.equals(Pair)) {
            //ranked by the value of their highest pair.
            // Hands with the same highest pair are ranked by the value of their other pair.
            // If these values are the same the hands are ranked by the value of the remaining card.
            return getResultSameOnePair(whiteHand, blackHand);
        }
        return new GameResult(ResultOption.T, null, "Tie");
    }

    private static GameResult getResultSameOnePair(Card[] whiteHand, Card[] blackHand) {

        OnePairContainer onePairContainerWhite = getOnePairContainer(whiteHand);
        List<Integer> whiteRanks = onePairContainerWhite.getRemainingValues().stream()
                .map(it -> it.order).sorted(Comparator.naturalOrder()).collect(Collectors.toList());

        OnePairContainer onePairContainerBlack = getOnePairContainer(blackHand);
        List<Integer> blackRanks = onePairContainerBlack.getRemainingValues().stream()
                .map(it -> it.order).sorted(Comparator.naturalOrder()).collect(Collectors.toList());


        Integer whiteRank = onePairContainerWhite.getTwoPairValue().order;
        Integer blackRank = onePairContainerBlack.getTwoPairValue().order;
        if (whiteRank > blackRank) {
            return new GameResult(ResultOption.W, Player.White, "with high card");
        } else if (whiteRank < blackRank) {
            return new GameResult(ResultOption.W, Player.Black, "with high card");
        } else {
            return getHighHandFromRanks(whiteRanks, blackRanks);
        }
    }

    public static GameResult getResultSameTwoPairs(Card[] whiteHand, Card[] blackHand) {
        //ranked by the value of their highest pair.
        // Hands with the same highest pair are ranked by the value of their other pair.
        // If these values are the same the hands are ranked by the value of the remaining card.

        TwoPairsContainer twoPairContainerWhite = getTwoPairContainer(whiteHand);
        List<Integer> whiteRanks = twoPairContainerWhite.getTwoPairsValues().stream()
                .map(it -> it.order).sorted(Comparator.naturalOrder()).collect(Collectors.toList());

        TwoPairsContainer twoPairContainerBlack = getTwoPairContainer(blackHand);
        List<Integer> blackRanks = twoPairContainerBlack.getTwoPairsValues().stream()
                .map(it -> it.order).sorted(Comparator.naturalOrder()).collect(Collectors.toList());

        if (whiteRanks.get(1) > blackRanks.get(1)) {
            return new GameResult(ResultOption.W, Player.White, "");
        } else if (whiteRanks.get(1) < blackRanks.get(1)) {
            return new GameResult(ResultOption.W, Player.Black, "");
        } else {
            if (whiteRanks.get(0) > blackRanks.get(0)) {
                return new GameResult(ResultOption.W, Player.White, "");
            } else if (whiteRanks.get(0) < blackRanks.get(0)) {
                return new GameResult(ResultOption.W, Player.Black, "");
            } else {
                Integer whiteRank = twoPairContainerWhite.getRemainingValue().order;
                Integer blackRank = twoPairContainerBlack.getRemainingValue().order;
                if (whiteRank > blackRank) {
                    return new GameResult(ResultOption.W, Player.White, "");
                } else if (whiteRank < blackRank) {
                    return new GameResult(ResultOption.W, Player.Black, "");
                } else {
                    return new GameResult(ResultOption.T, null, "");
                }
            }
        }

    }

    public static TwoPairsContainer getTwoPairContainer(Card[] hand) {
        TwoPairsContainer twoPairsContainer = new TwoPairsContainer();

        Set<Map.Entry<String, Integer>> entries = getValueFrequency(hand).entrySet();
        List<Value> whitePairs = new ArrayList<>();
        Value whileLeftOver = Value.TWO;
        for (Map.Entry<String, Integer> entry : entries) {
            if (entry.getValue().equals(2)) {
                whitePairs.add(Value.valueOf(entry.getKey()));
            } else {
                whileLeftOver = Value.valueOf(entry.getKey());
            }
        }
        twoPairsContainer.setTwoPairsValues(whitePairs);
        twoPairsContainer.setRemainingValue(whileLeftOver);
        return twoPairsContainer;
    }

    public static OnePairContainer getOnePairContainer(Card[] hand) {
        Set<Map.Entry<String, Integer>> whiteEntries = getValueFrequency(hand).entrySet();
        OnePairContainer onePairContainer = new OnePairContainer();
        Value whitePair = Value.TWO;
        List<Value> whileLeftOver = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : whiteEntries) {
            if (entry.getValue().equals(2)) {
                whitePair = Value.valueOf(entry.getKey());

            } else {
                whileLeftOver.add(Value.valueOf(entry.getKey()));
            }
        }
        onePairContainer.setTwoPairValue(whitePair);
        onePairContainer.setRemainingValues(whileLeftOver);
        return onePairContainer;
    }

    public static GameResult getHighHandFromRanks(List<Integer> whiteRanks, List<Integer> blackRanks) {
        whiteRanks.sort(Comparator.naturalOrder());
        blackRanks.sort(Comparator.naturalOrder());
        for (int i = whiteRanks.size() - 1; i >= 0; i--) {
            if (whiteRanks.get(i).compareTo(blackRanks.get(i)) > 0) {
                return new GameResult(ResultOption.W, Player.White, "with high card" + whiteRanks.get(i));
            } else if (whiteRanks.get(i).compareTo(blackRanks.get(i)) < 0) {
                return new GameResult(ResultOption.W, Player.Black, "with high card" + blackRanks.get(i));
            }
        }
        return new GameResult(ResultOption.T, null, "Tie");
    }

    public static GameResult getResultSameFullHouseAndThreeOfAKind(Card[] whiteHand, Card[] blackHand) {
        Rank whiteHandRank = Rank.valueOf(getRankOfTheMostFrequentCard(whiteHand, 3));
        Rank blackHandRank = Rank.valueOf(getRankOfTheMostFrequentCard(blackHand, 3));
        return getGameResultByRank(whiteHandRank, blackHandRank);
    }

    public static GameResult getResultSameFourOfKind(Card[] whiteHand, Card[] blackHand) {
        Rank whiteHandRank = Rank.valueOf(getRankOfTheMostFrequentCard(whiteHand, 4));
        Rank blackHandRank = Rank.valueOf(getRankOfTheMostFrequentCard(blackHand, 4));
        return getGameResultByRank(whiteHandRank, blackHandRank);
    }

    public static GameResult getResultSameHandsStraightFlush(Card[] whiteHand, Card[] blackHand) {
        Card whiteHighCard = getHighCard(whiteHand);
        Card blackHighCard = getHighCard(blackHand);
        if (whiteHighCard.compareTo(blackHighCard) > 0) {
            return new GameResult(ResultOption.W, Player.White, "with straight flush:" + whiteHighCard + "over" + blackHighCard);
        } else if (whiteHighCard.compareTo(blackHighCard) < 0) {
            return new GameResult(ResultOption.W, Player.Black, "with straight flush:" + blackHighCard + "over" + whiteHighCard);
        } else return new GameResult(ResultOption.T, null, "Tie");
    }

    public static Card getHighCard(Card[] hand) {
        return Arrays.stream(hand).max(Card::compareTo).get();
    }

    static String getRankOfTheMostFrequentCard(Card[] cards, int frequency) {
        Optional<Map.Entry<String, Integer>> first = getValueFrequency(cards)
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == frequency)
                .findFirst();
        if (first.isPresent()) {
            return first.get().getKey();
        } else {
            throw new IllegalArgumentException("there is not four of kind case");
        }
    }

    public static GameResult getResultHighHand(Card[] whiteHand, Card[] blackHand) {
        Arrays.sort(whiteHand);
        Arrays.sort(blackHand);
        for (int i = whiteHand.length - 1; i >= 0; i--) {
            if (whiteHand[i].compareTo(blackHand[i]) > 0) {
                return new GameResult(ResultOption.W, Player.White, "with high card" + whiteHand[i].getValue());
            } else if (whiteHand[i].compareTo(blackHand[i]) < 0) {
                return new GameResult(ResultOption.W, Player.Black, "with high card" + blackHand[i].getValue());
            }
        }
        return new GameResult(ResultOption.T, null, "Tie");
    }

}
