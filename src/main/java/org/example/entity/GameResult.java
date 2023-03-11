package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameResult {
    private final ResultOption result;
    private final Player player;

    private String description;


    public ResultOption getResult() {
        return result;
    }

    public Player getPlayer() {
        return player;
    }

    public String getDescription() {
        return description;
    }

}
