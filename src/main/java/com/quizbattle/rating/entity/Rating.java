package com.quizbattle.rating.entity;

import com.quizbattle.user.entity.User;
import com.quizbattle.subject.entity.Subject;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;


@Entity
@Table(
        name = "ratings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_subject",
                        columnNames = {"user_id", "subject_id"}
                )
        }
)
public class Rating {

    @Id
    @GeneratedValue
    private UUID id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id")
    private Subject subject;


    @Column(nullable = false)
    private int rating;


    @Column(name = "matches_played", nullable = false)
    private int matchesPlayed;


    @Column(nullable = false)
    private int wins;


    @Column(nullable = false)
    private int losses;


    @Column(name = "created_at", nullable = false)
    private Instant createdAt;



    public Rating() {
    }


    public Rating(User user, Subject subject) {

        this.user = user;

        this.subject = subject;

        this.rating = 1200;

        this.matchesPlayed = 0;

        this.wins = 0;

        this.losses = 0;

        this.createdAt = Instant.now();
    }


    public UUID getId() {
        return id;
    }


    public User getUser() {
        return user;
    }


    public Subject getSubject() {
        return subject;
    }


    public int getRating() {
        return rating;
    }


    public void setRating(int rating) {
        this.rating = rating;
    }


    public int getMatchesPlayed() {
        return matchesPlayed;
    }


    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }


    public int getWins() {
        return wins;
    }


    public void setWins(int wins) {
        this.wins = wins;
    }


    public int getLosses() {
        return losses;
    }


    public void setLosses(int losses) {
        this.losses = losses;
    }


    public Instant getCreatedAt() {
        return createdAt;
    }

}