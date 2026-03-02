package com.quizbattle.question.service;

import com.quizbattle.question.entity.Question;
import com.quizbattle.question.repository.QuestionRepository;

import com.quizbattle.subject.entity.Subject;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;



@Service
public class QuestionService {

    private final QuestionRepository questionRepository;



    public QuestionService(
            QuestionRepository questionRepository
    ) {
        this.questionRepository = questionRepository;
    }



    public String getDifficultyFromRating(
            int rating
    ) {

        if (rating < 1000)
            return "EASY";

        if (rating < 1400)
            return "MEDIUM";

        if (rating < 1800)
            return "HARD";

        return "EXPERT";

    }



    public List<Question> getQuestionsForMatch(
            Subject subject,
            int rating,
            int count
    ) {

        String difficulty =
                getDifficultyFromRating(rating);


        return questionRepository
                .findRandomQuestions(
                        subject.getId(),
                        difficulty,
                        count
                );

    }



    public boolean isCorrectAnswer(
            Question question,
            String userAnswer
    ) {

        return question
                .getRightAnswer()
                .trim()
                .equalsIgnoreCase(
                        userAnswer.trim()
                );

    }



    public String getAnswerExplanation(
            Question question,
            boolean isPremiumUser
    ) {

        if (!isPremiumUser)
            return null;

        return question.getAnswerDescription();

    }

}