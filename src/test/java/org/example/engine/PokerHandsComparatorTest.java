package org.example.engine;

import org.example.entity.*;
import org.example.parser.InputParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandsComparatorTest {
    PokerHandsComparator comparator = new PokerHandsComparator();

    Card jackC;
    Card jackS;
    Card jackH;
    Card jackD;
    Card tD;
    Card nineD;
    Card eightD;
    Card sevenD;
    Card sevenH;
    Card[] cardsStraightFlash;
    Card[] cardsFourOfKind;
    Card[] cardsFullHouse;

    @BeforeEach
    void SetUp() {
        jackC = new Card(Club.C, Value.JACK);
        jackS = new Card(Club.S, Value.JACK);
        jackH = new Card(Club.H, Value.JACK);
        jackD = new Card(Club.D, Value.JACK);
        tD = new Card(Club.D, Value.TEN);
        nineD = new Card(Club.D, Value.NINE);
        eightD = new Card(Club.D, Value.EIGHT);
        sevenD = new Card(Club.D, Value.SEVEN);
        sevenH = new Card(Club.H, Value.SEVEN);
        cardsStraightFlash = new Card[]{jackD, tD, nineD, eightD, sevenD};
        cardsFourOfKind = new Card[]{jackC, jackD, jackS, jackH, tD};
        cardsFullHouse = new Card[]{jackD, jackC, jackS, sevenD, sevenH};

    }

    @Test
    void compareHands() {

    }

    @Test
    void shouldReturnTrueIfStraightFlush() {
        //5 cards of the same suit with consecutive values
        assertTrue(comparator.isStraightFlush(cardsStraightFlash));
        assertFalse(comparator.isStraightFlush(cardsFourOfKind));
    }

    @Test
    void shouldReturnTrueIfFourOfKind() {
        //4 cards with the same value. Ranked by the value of the 4 cards.
        assertTrue(comparator.isFourOfAKind(cardsFourOfKind));
        assertFalse(comparator.isFourOfAKind(cardsStraightFlash));
    }

    @Test
    void shouldReturnTrueIfFullHouse() {
        assertTrue(comparator.isFullHouse(cardsFullHouse));
    }

    @Test
    void shouldReturnTrueIfFlushTest() {
        String flash = "2H 3H 7H 9H QH";
        String notFlash = "2D 3H 5C 9S KH";
        Card[] flashCards = parseStringToCards(flash);
        Card[] notFlushCards = parseStringToCards(notFlash);

        assertTrue(comparator.isFlush(flashCards));
        assertFalse(comparator.isFlush(notFlushCards));
    }

    @Test
    void shouldReturnTrueIfStraightPresentTest() {
        String straight = "2D 3H 4C 5S 6H";
        String notStraight = "2D 3H 5C 9S KH";
        Card[] straightCards = parseStringToCards(straight);
        Card[] notStraightCards = parseStringToCards(notStraight);

        assertTrue(comparator.isStraight(straightCards));
        assertFalse(comparator.isFlush(notStraightCards));
    }

    @Test
    void shouldReturnTrueIfTwoPairsPresentTest() {
        String pairs = "2D 2H 4C 4S 6H";
        String notPairs = "2D 3H 5C 9S KH";
        Card[] pairsCards = parseStringToCards(pairs);
        Card[] notPairsCards = parseStringToCards(notPairs);
        assertTrue(comparator.isTwoPairs(pairsCards));
        assertFalse(comparator.isTwoPairs(notPairsCards));
    }

    @Test
    void shouldReturnTrueIfOnePairPresentTest() {
        String pairs = "2D 2H 4C 8S 6H";
        String notPairs = "2D 3H 5C 9S KH";
        Card[] pairsCards = parseStringToCards(pairs);
        Card[] notPairsCards = parseStringToCards(notPairs);
        assertTrue(comparator.isOnePairPresent(pairsCards));
        assertFalse(comparator.isOnePairPresent(notPairsCards));
    }

    @Test
    void getResultSameFourOfKindTest() {
        String white = "2D 2H 2C 2S 6H";
        String black = "3D 3H 3C 3S 6H";
        Card[] waitCards = parseStringToCards(white);
        Card[] blackCards = parseStringToCards(black);

        GameResult resultSameFourOfKind = comparator.getResultSameFourOfKind(waitCards, blackCards);

        assertEquals(Player.Black, resultSameFourOfKind.getPlayer());
        assertEquals(ResultOption.W, resultSameFourOfKind.getResult());
    }

    @Test
    void getResultSameRanksFullHouse() {
        Card[] whiteCards = parseStringToCards("2D 2H 2C 5S 6H");
        Card[] blackCards = parseStringToCards("3D 3H 3C 8S 6H");

        GameResult resultSameFourOfKind = comparator.getResultSameFullHouse(whiteCards, blackCards);

        assertEquals(Player.Black, resultSameFourOfKind.getPlayer());
        assertEquals(ResultOption.W, resultSameFourOfKind.getResult());
    }
    @Test
    void getResultHighHandTest(){
        Card[] whiteCards = parseStringToCards("2C 3H 4S 8C KH");
        Card[] blackCards = parseStringToCards("2H 3D 5S 9C KD");

        GameResult resultSameFourOfKind = comparator.getResultHighHand(whiteCards, blackCards);

        assertEquals(Player.Black, resultSameFourOfKind.getPlayer());
        assertEquals(ResultOption.W, resultSameFourOfKind.getResult());
    }


    public Card[] parseStringToCards(String hand) {
        return Arrays.stream(hand.split(" ")).map(
                InputParser::getCard
        ).toArray(Card[]::new);
    }

}