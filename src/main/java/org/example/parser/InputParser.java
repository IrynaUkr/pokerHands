package org.example.parser;

import org.example.entity.Card;
import org.example.entity.Club;
import org.example.entity.Player;
import org.example.entity.Value;

import java.util.Arrays;

public class InputParser {
    public Card[] parsInput(String input, Player player) {
        int beginIndex = input.indexOf(player.name() + ":") + player.name().length() + 1;
        String hand = input.trim().substring(beginIndex + 1, beginIndex + 15);
        return Arrays.stream(hand.split(" ")).map(
                InputParser::getCard
        ).toArray(Card[]::new);
    }

    public static Card getCard(String glyph) {
        Club club = Club.valueOf(glyph.substring(1, 2));
        Value value = Value.getValueByChar(glyph.substring(0, 1));
        return new Card(club, value);
    }

}
