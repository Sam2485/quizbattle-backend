package com.quizbattle.friend.dto;

import java.time.Instant;
import java.util.UUID;

public class FriendResponseDTO {

    private UUID id;
    private UUID senderId;
    private UUID receiverId;
    private String status;
    private Instant createdAt;

    public FriendResponseDTO(
            UUID id,
            UUID senderId,
            UUID receiverId,
            String status,
            Instant createdAt
    ) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public UUID getSenderId() { return senderId; }
    public UUID getReceiverId() { return receiverId; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}