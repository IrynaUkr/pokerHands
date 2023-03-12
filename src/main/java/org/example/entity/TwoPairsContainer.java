package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TwoPairsContainer {
    List<Value> twoPairsValues;
    Value remainingValue;

}
