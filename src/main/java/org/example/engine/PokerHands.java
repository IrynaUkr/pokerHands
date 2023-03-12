package org.example.engine;

import org.example.entity.*;
import org.example.parser.InputParser;

import static org.example.engine.RankCalculator.calculateRank;
import static org.example.engine.SameRankHandsService.getGameResultIfSameHandsRanks;

public class PokerHands {

    public GameResult playPoker(String line) {
        InputParser parser = new InputParser();
        Card[] whiteHand = parser.parsInput(line, Player.White);
        Card[] blackHand = parser.parsInput(line, Player.Black);

        Rank whiteHandRank = calculateRank(whiteHand);
        Rank blackHandRank = calculateRank(blackHand);

        if (blackHandRank == whiteHandRank) {
            System.out.println("hands have the same ranks:" + whiteHandRank );
            return getGameResultIfSameHandsRanks(whiteHandRank,  whiteHand, blackHand);
        }
        System.out.println("hands have different ranks: white: "+whiteHandRank+ ";  black: "+ blackHandRank  );
        return getGameResultByRank(whiteHandRank, blackHandRank);
    }

    static GameResult getGameResultByRank(Rank whiteHandRank, Rank blackHandRank) {
        if (whiteHandRank.rank > blackHandRank.rank) {
            System.out.println("whiteHandRank.rank > blackHandRank.rank: "+ whiteHandRank.rank+" > "+ blackHandRank.rank);
            return new GameResult(ResultOption.W, Player.White, " with " + whiteHandRank);
        } else if (whiteHandRank.rank < blackHandRank.rank) {
            System.out.println("whiteHandRank.rank < blackHandRank.rank: " + whiteHandRank.rank+" < "+ blackHandRank.rank);
            return new GameResult(ResultOption.W, Player.Black, " with " + blackHandRank);
        }
        return new GameResult(ResultOption.T, Player.Tie, "Tie");
    }
}
