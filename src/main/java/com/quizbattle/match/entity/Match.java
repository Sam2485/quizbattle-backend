package com.quizbattle.match.entity;

import com.quizbattle.user.entity.User;
import com.quizbattle.subject.entity.Subject;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue
    private UUID id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "player1_id")
    private User player1;


    @ManyToOne(optional = false)
    @JoinColumn(name = "player2_id")
    private User player2;


    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id")
    private Subject subject;


    @ManyToOne
    @JoinColumn(name = "winner_id")
    private User winner;


    @Column(nullable = false)
    private String status;


    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


    @Column(name = "started_at")
    private Instant startedAt;


    @Column(name = "finished_at")
    private Instant finishedAt;



    public Match() {
    }


    public Match(User player1, User player2, Subject subject) {

        this.player1 = player1;

        this.player2 = player2;

        this.subject = subject;

        this.status = "WAITING";

        this.createdAt = Instant.now();
    }



    public UUID getId() {
        return id;
    }


    public User getPlayer1() {
        return player1;
    }


    public User getPlayer2() {
        return player2;
    }


    public Subject getSubject() {
        return subject;
    }


    public User getWinner() {
        return winner;
    }


    public void setWinner(User winner) {
        this.winner = winner;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public Instant getCreatedAt() {
        return createdAt;
    }


    public Instant getStartedAt() {
        return startedAt;
    }


    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }


    public Instant getFinishedAt() {
        return finishedAt;
    }


    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

}