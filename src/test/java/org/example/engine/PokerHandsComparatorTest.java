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
    Card[] cardsStraightFlash;
    Card[] cardsFourOfKind;

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
        cardsStraightFlash = new Card []{jackD,tD,nineD, eightD, sevenD};
        cardsFourOfKind = new Card[]{jackC, jackD, jackS, jackH, tD};
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



}