package org.example.engine;

import org.example.entity.*;

import static org.example.engine.RankCalculator.calculateRank;
import static org.example.engine.SameRankHandsService.getGameResultIfSameHandsRanks;

public class PokerHandsComparator implements HandsComparator {
    @Override
    public GameResult compareHands(Card[] blackHand, Card[] whiteHand) {
        Rank blackHandRank = calculateRank(blackHand);
        Rank whiteHandRank = calculateRank(whiteHand);

        if (blackHandRank == whiteHandRank) {
            return getGameResultIfSameHandsRanks(whiteHandRank, blackHand, whiteHand);
        }
        return getGameResultByRank(whiteHandRank, blackHandRank);
    }

    static GameResult getGameResultByRank(Rank whiteHandRank, Rank blackHandRank) {
        if (whiteHandRank.rank > blackHandRank.rank) {
            return new GameResult(ResultOption.W, Player.White, "with " + whiteHandRank);
        } else if (whiteHandRank.rank < blackHandRank.rank) {
            return new GameResult(ResultOption.W, Player.Black, "with " + blackHandRank);
        }
        return new GameResult(ResultOption.T, null, "Tie");
    }


}
