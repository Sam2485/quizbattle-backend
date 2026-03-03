package com.quizbattle.rating.dto;

import java.util.UUID;

public class RatingResponseDTO {

    private UUID subjectId;
    private int rating;
    private int matchesPlayed;

    public RatingResponseDTO(
            UUID subjectId,
            int rating,
            int matchesPlayed
    ) {
        this.subjectId = subjectId;
        this.rating = rating;
        this.matchesPlayed = matchesPlayed;
    }

    public UUID getSubjectId() { return subjectId; }
    public int getRating() { return rating; }
    public int getMatchesPlayed() { return matchesPlayed; }
}
