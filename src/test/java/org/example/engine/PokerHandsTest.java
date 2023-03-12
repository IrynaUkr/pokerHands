package org.example.engine;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PokerHandsTest {
PokerHands pokerHands;
    @ParameterizedTest
    @CsvFileSource(resources = "/dataPokerLines.csv", numLinesToSkip = 1)
    void playPokerTest(String line, String winner) {
        pokerHands = new PokerHands();
        String actualWinner = pokerHands.playPoker(line).getPlayer().toString();
        assertEquals(winner, actualWinner);
    }

}