package org.example.engine;

import org.example.entity.Card;
import org.example.entity.Club;
import org.example.entity.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandsComparatorTest {
    PokerHandsComparator comparator =new PokerHandsComparator();
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


}