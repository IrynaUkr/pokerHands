package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class GameResult {
    private final ResultOption result;
    private final Player player;
    private String description;

}
