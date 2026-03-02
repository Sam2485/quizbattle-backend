package com.quizbattle.question.entity;

import com.quizbattle.subject.entity.Subject;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;



@Entity
@Table(
        name = "questions",
        indexes = {
                @Index(name = "idx_question_subject", columnList = "subject_id"),
                @Index(name = "idx_question_difficulty", columnList = "difficulty_level")
        }
)
public class Question {

    @Id
    @GeneratedValue
    private UUID id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id")
    private Subject subject;


    @Column(name = "question_title", nullable = false, length = 1000)
    private String questionTitle;


    @Column(nullable = false)
    private String option1;


    @Column(nullable = false)
    private String option2;


    @Column(nullable = false)
    private String option3;


    @Column(nullable = false)
    private String option4;


    @Column(name = "right_answer", nullable = false)
    private String rightAnswer;


    @Column(name = "difficulty_level", nullable = false)
    private String difficultyLevel;


    @Column(name = "answer_description", length = 2000)
    private String answerDescription;


    @Column(name = "created_at", nullable = false)
    private Instant createdAt;



    public Question() {
    }



    public Question(
            Subject subject,
            String questionTitle,
            String option1,
            String option2,
            String option3,
            String option4,
            String rightAnswer,
            String difficultyLevel,
            String answerDescription
    ) {

        this.subject = subject;

        this.questionTitle = questionTitle;

        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;

        this.rightAnswer = rightAnswer;

        this.difficultyLevel = difficultyLevel;

        this.answerDescription = answerDescription;

        this.createdAt = Instant.now();
    }



    public UUID getId() {
        return id;
    }


    public Subject getSubject() {
        return subject;
    }


    public String getQuestionTitle() {
        return questionTitle;
    }


    public String getOption1() {
        return option1;
    }


    public String getOption2() {
        return option2;
    }


    public String getOption3() {
        return option3;
    }


    public String getOption4() {
        return option4;
    }


    public String getRightAnswer() {
        return rightAnswer;
    }


    public String getDifficultyLevel() {
        return difficultyLevel;
    }


    public String getAnswerDescription() {
        return answerDescription;
    }


    public Instant getCreatedAt() {
        return createdAt;
    }

}