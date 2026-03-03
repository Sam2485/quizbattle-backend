package com.quizbattle.leaderboard.dto;

import java.util.UUID;

public class LeaderboardEntryDTO {

    private UUID userId;
    private int rating;
    private long rank;

    public LeaderboardEntryDTO(
            UUID userId,
            int rating,
            long rank
    ) {
        this.userId = userId;
        this.rating = rating;
        this.rank = rank;
    }

    public UUID getUserId() {
        return userId;
    }

    public int getRating() {
        return rating;
    }

    public long getRank() {
        return rank;
    }
}