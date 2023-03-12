package org.example.engine;

import org.example.entity.*;
import org.example.parser.InputParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.engine.SameRankHandsService.*;
import static org.junit.jupiter.api.Assertions.*;

class SameRankHandsServiceTest {

    @Test
    void getGameResultIfSameHandsRanksTest() {

    }

    @Test
    void getResultSameTwoPairsTest() {
        Card[] whiteCards = parseStringToCards("2D 2H 4C 4S 6H");
        Card[] blackCards = parseStringToCards("3D 3H QC QS 6H");
        GameResult resultSameTwoPairs = getResultSameTwoPairs(whiteCards, blackCards);

        assertEquals(Player.Black, resultSameTwoPairs.getPlayer());
        assertEquals(ResultOption.W, resultSameTwoPairs.getResult());
    }


    @Test
    void getResultSameFullHouseAndThreeOfAKindTest() {
        Card[] whiteCards = parseStringToCards("2D 2H 2C 5S 6H");
        Card[] blackCards = parseStringToCards("3D 3H 3C 8S 6H");

        GameResult resultSameFourOfKind = getResultSameFullHouseAndThreeOfAKind(whiteCards, blackCards);

        assertEquals(Player.Black, resultSameFourOfKind.getPlayer());
        assertEquals(ResultOption.W, resultSameFourOfKind.getResult());
    }

    @Test
    void getResultSameFourOfKindTest() {
        Card[] waitCards = parseStringToCards("QD QH QC QS 6H");
        Card[] blackCards = parseStringToCards("3D 3H 3C 3S 6H");

        GameResult resultSameFourOfKind = getResultSameFourOfKind(waitCards, blackCards);

        assertEquals(Player.White, resultSameFourOfKind.getPlayer());
        assertEquals(ResultOption.W, resultSameFourOfKind.getResult());
    }

    @Test
    void getResultSameHandsStraightFlushTest() {
        Card[] waitCards = parseStringToCards("2D 3D 4D 5D 6D");
        Card[] blackCards = parseStringToCards("7S 8S 9S TS JS");

        GameResult result = getResultSameHandsStraightFlush(waitCards, blackCards);

        assertEquals(Player.Black, result.getPlayer());
        assertEquals(ResultOption.W, result.getResult());
    }

    @Test
    void getHighCardTest() {
        Card[] blackCards = parseStringToCards("7S 8S 9S TS JS");
        Card highCard = getHighCard(blackCards);
        Card expected = new Card(Club.S, Value.JACK);
        assertEquals(expected, highCard);
    }


    @Test
    void getResultHighHandTest() {
        Card[] whiteCards = parseStringToCards("2C 3H 4S 8C KH");
        Card[] blackCards = parseStringToCards("2H 3D 5S 9C KD");

        GameResult resultSameFourOfKind = getResultHighHand(whiteCards, blackCards);

        assertEquals(Player.Black, resultSameFourOfKind.getPlayer());
        assertEquals(ResultOption.W, resultSameFourOfKind.getResult());
    }

    @Test
    void getHighHandFromRanksTest() {
        List<Integer> whiteRanks = Arrays.asList(10, 9, 8, 4, 5);
        List<Integer> blackRanks = Arrays.asList(4, 5, 6, 7, 8);
        GameResult highHandFromRanks = getHighHandFromRanks(whiteRanks, blackRanks);
        assertEquals(Player.White, highHandFromRanks.getPlayer());
    }


    public Card[] parseStringToCards(String hand) {
        return Arrays.stream(hand.split(" ")).map(
                InputParser::getCard
        ).toArray(Card[]::new);
    }

}