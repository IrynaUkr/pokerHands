package org.example.parser;

import org.example.entity.Card;
import org.example.entity.Club;
import org.example.entity.Player;
import org.example.entity.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputParserTest {
    InputParser parser;

    @Test
    void shouldParseInputByTwoPlayersTest() {
        InputParser parser = new InputParser();
        String input = "Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH";

        Card[] cardsWhiteHand = parser.parsInput(input, Player.White);
        Card[] cardsBlackHand = parser.parsInput(input, Player.Black);
        assertEquals(5, cardsBlackHand.length);
        assertEquals(5, cardsWhiteHand.length);

    }

    @Test
    void shouldParseInputAndGetValidCardTest() {
       parser = new InputParser();
        String input = "Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH";

        Card[] cardsWhiteHand = parser.parsInput(input, Player.White);

        assertEquals(Club.C, cardsWhiteHand[0].getClub());
        assertEquals(Value.TWO, cardsWhiteHand[0].getValue());
    }

}