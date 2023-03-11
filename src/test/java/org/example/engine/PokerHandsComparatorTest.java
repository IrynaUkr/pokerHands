package org.example.engine;

import org.example.entity.Card;
import org.example.entity.Club;
import org.example.entity.Value;
import org.example.parser.InputParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandsComparatorTest {
    PokerHandsComparator comparator =new PokerHandsComparator();
    InputParser inputParser = new InputParser();

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
        cardsStraightFlash = new Card []{jackD,tD,nineD, eightD, sevenD};
        cardsFourOfKind = new Card[]{jackC, jackD, jackS, jackH, tD};
        cardsFullHouse = new Card[]{jackD,jackC,jackS,sevenD,sevenH };

    }

    @Test
    void compareHands() {

    }

    @Test
    void shouldReturnTrueIfStraightFlush(){
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
    void shouldReturnTrueIfFullHouse(){
        assertTrue(comparator.isFullHouse(cardsFullHouse));
    }

    @Test
    void shouldReturnTrueIfFlushTest(){
        String flash = "2H 3H 7H 9H QH";
        String notFlash ="2D 3H 5C 9S KH";
        Card[] flashCards = parseStringToCard(flash);
        Card[] notFlushCards = parseStringToCard(notFlash);

        assertTrue(comparator.isFlush(flashCards));
        assertFalse(comparator.isFlush(notFlushCards));
    }

    public Card[] parseStringToCard(String hand){
        return Arrays.stream(hand.split(" ")).map(
                x -> inputParser.getCard(x)
        ).toArray(Card[]::new);
    }


}