package com.quizbattle.match.entity;

import com.quizbattle.question.entity.Question;
import com.quizbattle.user.entity.User;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;



@Entity
@Table(
        name = "match_answers",
        indexes = {
                @Index(name = "idx_match_answers_match", columnList = "match_id"),
                @Index(name = "idx_match_answers_user", columnList = "user_id")
        }
)
public class MatchAnswer {

    @Id
    @GeneratedValue
    private UUID id;



    @ManyToOne(optional = false)
    @JoinColumn(name = "match_id")
    private Match match;



    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;



    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    private Question question;



    @Column(name = "submitted_answer", nullable = false)
    private String submittedAnswer;



    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;



    @Column(name = "time_taken_ms")
    private Long timeTakenMs;



    @Column(name = "answered_at", nullable = false)
    private Instant answeredAt;



    public MatchAnswer() {
    }



    public MatchAnswer(
            Match match,
            User user,
            Question question,
            String submittedAnswer,
            boolean isCorrect,
            Long timeTakenMs
    ) {

        this.match = match;
        this.user = user;
        this.question = question;
        this.submittedAnswer = submittedAnswer;
        this.isCorrect = isCorrect;
        this.timeTakenMs = timeTakenMs;
        this.answeredAt = Instant.now();
    }



    public UUID getId() {
        return id;
    }


    public Match getMatch() {
        return match;
    }


    public User getUser() {
        return user;
    }


    public Question getQuestion() {
        return question;
    }


    public String getSubmittedAnswer() {
        return submittedAnswer;
    }


    public boolean isCorrect() {
        return isCorrect;
    }


    public Long getTimeTakenMs() {
        return timeTakenMs;
    }


    public Instant getAnsweredAt() {
        return answeredAt;
    }

}