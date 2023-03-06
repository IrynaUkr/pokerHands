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
        switch (glif) {
            case "2":
                return TWO;
            case "3":
                return THREE;
            case "4":
                return FOUR;
            case "5":
                return FIVE;
            case "6":
                return SIX;
            case "7":
                return SEVEN;
            case "8":
                return EIGHT;
            case "9":
                return NINE;
            case "T":
                return TEN;
            case "J":
                return JACK;
            case "Q":
                return QUEEN;
            case "K":
                return KING;
            case "A":
                return ACE;
            default:
                throw new IllegalStateException("Unexpected value: " + glif);
        }
    }
}
