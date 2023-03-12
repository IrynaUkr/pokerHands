package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@AllArgsConstructor
@Getter
@ToString
public class Card implements Comparable<Card> {
    private final Club club;
    private final Value value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return club == card.club && value == card.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClub(), getValue());
    }

    @Override
    public int compareTo(Card otherCard) {
        return this.getValue().order - otherCard.getValue().order;
    }

}
