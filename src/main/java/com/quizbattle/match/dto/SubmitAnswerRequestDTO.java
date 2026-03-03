package com.quizbattle.match.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class SubmitAnswerRequestDTO {

    @NotNull
    private UUID questionId;

    @NotNull
    private String answer;

    @NotNull
    private Long timeTakenMs;



    public UUID getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public Long getTimeTakenMs() {
        return timeTakenMs;
    }
}