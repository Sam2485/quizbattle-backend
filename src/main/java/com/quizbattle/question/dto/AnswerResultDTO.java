package com.quizbattle.question.dto;

public class AnswerResultDTO {

    private boolean correct;
    private String explanation; // only if premium



    public AnswerResultDTO(
            boolean correct,
            String explanation
    ) {
        this.correct = correct;
        this.explanation = explanation;
    }



    public boolean isCorrect() {
        return correct;
    }

    public String getExplanation() {
        return explanation;
    }
}