package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OnePairContainer {
    List<Value> remainingValues;
    Value twoPairValue;

}
