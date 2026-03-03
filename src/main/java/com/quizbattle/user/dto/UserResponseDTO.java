package com.quizbattle.user.dto;

import java.time.Instant;
import java.util.UUID;

public class UserResponseDTO {

    private UUID id;
    private String firebaseUid;
    private String email;
    private String displayName;
    private Instant createdAt;
    private Instant lastActive;

    public UserResponseDTO(
            UUID id,
            String firebaseUid,
            String email,
            String displayName,
            Instant createdAt,
            Instant lastActive
    ) {
        this.id = id;
        this.firebaseUid = firebaseUid;
        this.email = email;
        this.displayName = displayName;
        this.createdAt = createdAt;
        this.lastActive = lastActive;
    }

    public UUID getId() {
        return id;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getLastActive() {
        return lastActive;
    }
}