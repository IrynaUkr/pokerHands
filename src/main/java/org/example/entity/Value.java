package org.example.entity;

public enum Value {
    TWO("2", 0),
    THREE("3", 1),
    FOUR("4", 2),
    FIVE("5", 3),
    SIX("6", 4),
    SEVEN("7", 5),
    EIGHT("8", 6),
    NINE("9", 7),
    TEN("T", 8),
    JACK("J", 9),
    QUEEN("Q", 10),
    KING("K", 11),
    ACE("A", 12);

    public final String symbol;
    public final Integer order;

    Value(String symbol, Integer order) {
        this.symbol = symbol;
        this.order = order;
    }

    public static Value getValueByChar(String glif) {
        return switch (glif) {
            case "2" -> TWO;
            case "3" -> THREE;
            case "4" -> FOUR;
            case "5" -> FIVE;
            case "6" -> SIX;
            case "7" -> SEVEN;
            case "8" -> EIGHT;
            case "9" -> NINE;
            case "T" -> TEN;
            case "J" -> JACK;
            case "Q" -> QUEEN;
            case "K" -> KING;
            case "A" -> ACE;
            default -> throw new IllegalStateException("Unexpected value: " + glif);
        };
    }
}
