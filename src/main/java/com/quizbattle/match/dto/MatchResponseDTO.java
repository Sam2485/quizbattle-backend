package com.quizbattle.match.dto;

import java.time.Instant;
import java.util.UUID;

public class MatchResponseDTO {

    private UUID matchId;
    private UUID player1Id;
    private UUID player2Id;
    private UUID winnerId;
    private String status;
    private Instant startedAt;
    private Instant finishedAt;



    public MatchResponseDTO(
            UUID matchId,
            UUID player1Id,
            UUID player2Id,
            UUID winnerId,
            String status,
            Instant startedAt,
            Instant finishedAt
    ) {
        this.matchId = matchId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.winnerId = winnerId;
        this.status = status;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }



    public UUID getMatchId() {
        return matchId;
    }

    public UUID getPlayer1Id() {
        return player1Id;
    }

    public UUID getPlayer2Id() {
        return player2Id;
    }

    public UUID getWinnerId() {
        return winnerId;
    }

    public String getStatus() {
        return status;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }
}