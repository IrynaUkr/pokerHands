package org.example.engine;

import org.example.entity.Card;
import org.example.entity.Rank;
import org.example.parser.InputParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;
import java.util.HashMap;

import static org.example.engine.RankCalculator.*;
import static org.junit.jupiter.api.Assertions.*;

class RankCalculatorTest {


    @ParameterizedTest
    @CsvFileSource(resources = "/dataRank.csv", numLinesToSkip = 1)
    void calculateRankTest(String hand, String rankValue) {
        Card[] cards = parseStringToCards(hand);
        Rank expectedRank = Rank.valueOf(rankValue);
        Rank actualRank = calculateRank(cards);

        assertEquals(expectedRank,actualRank);
    }
    @Test
    void isStraightFlushTest() {
        Card[] straightFlash = parseStringToCards("2H 3H 4H 5H 6H");
        Card[] notStraightFlash = parseStringToCards("TD 3H QC 5S 2H");

        assertTrue(isStraightFlush(straightFlash));
        assertFalse(isStraightFlush(notStraightFlash));
    }

    @Test
    void isFourOfAKindTest() {
        Card[] fourOfAKind = parseStringToCards("2S 2D 2H 2C 6H");
        Card[] notFourOfAKind = parseStringToCards("TD 3H QC 5S 2H");

        assertTrue(isFourOfAKind(fourOfAKind));
        assertFalse(isFourOfAKind(notFourOfAKind));
    }

    @Test
    void isFullHouseTest() {
        Card[] fullHouse = parseStringToCards("2S 2D 2H 4C 4H");
        Card[] notFullHouse = parseStringToCards("TD 3H QC 5S 2H");

        assertTrue(isFullHouse(fullHouse));
        assertFalse(isFullHouse(notFullHouse));
    }

    @Test
    void isFlushTest() {
        Card[] flush = parseStringToCards("3D TD QD 4D AD");
        Card[] notFlush = parseStringToCards("TD 3H QC 5S 2H");

        assertTrue(isFlush(flush));
        assertFalse(isFlush(notFlush));
    }

    @Test
    void isStraightTest() {
        Card[] straight = parseStringToCards("3D 4S 5D 6H 7S");
        Card[] notStraight = parseStringToCards("TD 3H QC 5S 2H");

        assertTrue(isStraight(straight));
        assertFalse(isStraight(notStraight));
    }

    @Test
    void isThreeOfKindTest() {
        Card[] threeOfKind = parseStringToCards("3D 3S 3D 6H 7S");
        Card[] notThreeOfKind = parseStringToCards("TD 3H QC 5S 2H");

        assertTrue(isThreeOfKind(threeOfKind));
        assertFalse(isThreeOfKind(notThreeOfKind));
    }

    @Test
    void isTwoPairsTest() {
        Card[] twoPairs = parseStringToCards("3D 3S 6D 7H 7S");
        Card[] notTwoPairs = parseStringToCards("TD 3H QC 5S 2H");

        assertTrue(isTwoPairs(twoPairs));
        assertFalse(isTwoPairs(notTwoPairs));
    }

    @Test
    void isOnePairTest() {
        Card[] onePair = parseStringToCards("3D 3S QD 7H TS");
        Card[] notOnePair = parseStringToCards("TD 3H QC 5S 2H");

        assertTrue(isOnePair(onePair));
        assertFalse(isOnePair(notOnePair));
    }

    @Test
    void getValueFrequencyTest() {
        Card[] onePair = parseStringToCards("3D 3S QD 7H TS");
        Card[] twoPairs = parseStringToCards("3D 3S 6D 7H 7S");
        Card[] highHand = parseStringToCards("3D 9S AD 7H QS");

        HashMap<String, Integer> valueFrequencyOnePair = getValueFrequency(onePair);
        HashMap<String, Integer> valueFrequencyTwoPairs = getValueFrequency(twoPairs);
        HashMap<String, Integer> valueFrequencyHighHand = getValueFrequency(highHand);

        assertEquals(4, valueFrequencyOnePair.size());
        assertEquals(3,valueFrequencyTwoPairs.size());
        assertEquals(5, valueFrequencyHighHand.size());
    }
    public Card[] parseStringToCards(String hand) {
        return Arrays.stream(hand.split(" ")).map(
                InputParser::getCard
        ).toArray(Card[]::new);
    }

}