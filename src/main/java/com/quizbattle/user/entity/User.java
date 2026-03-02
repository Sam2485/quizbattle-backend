package com.quizbattle.user.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_users_firebase_uid", columnList = "firebase_uid", unique = true),
                @Index(name = "idx_users_email", columnList = "email")
        }
)
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "firebase_uid", nullable = false, unique = true)
    private String firebaseUid;

    @Column(nullable = false)
    private String email;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "last_active")
    private Instant lastActive;


    public User() {
    }


    public User(String firebaseUid, String email, String displayName) {
        this.firebaseUid = firebaseUid;
        this.email = email;
        this.displayName = displayName;
        this.createdAt = Instant.now();
        this.lastActive = Instant.now();
    }


    public UUID getId() {
        return id;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getLastActive() {
        return lastActive;
    }

    public void setLastActive(Instant lastActive) {
        this.lastActive = lastActive;
    }

}