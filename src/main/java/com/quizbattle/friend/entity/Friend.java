package com.quizbattle.friend.entity;

import com.quizbattle.user.entity.User;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;



@Entity
@Table(
        name = "friends",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_friend_pair",
                        columnNames = {"sender_id", "receiver_id"}
                )
        }
)
public class Friend {

    @Id
    @GeneratedValue
    private UUID id;



    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id")
    private User sender;



    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver_id")
    private User receiver;



    @Column(nullable = false)
    private String status;



    @Column(name = "created_at", nullable = false)
    private Instant createdAt;



    public Friend() {
    }



    public Friend(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = "PENDING";
        this.createdAt = Instant.now();
    }



    public UUID getId() {
        return id;
    }


    public User getSender() {
        return sender;
    }


    public User getReceiver() {
        return receiver;
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

}