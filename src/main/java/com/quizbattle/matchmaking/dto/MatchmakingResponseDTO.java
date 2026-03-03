package com.quizbattle.matchmaking.dto;

import java.util.UUID;

public class MatchmakingResponseDTO {

    private String status; // WAITING or MATCH_FOUND
    private UUID matchId;

    public MatchmakingResponseDTO(String status, UUID matchId) {
        this.status = status;
        this.matchId = matchId;
    }

    public String getStatus() { return status; }
    public UUID getMatchId() { return matchId; }
}
