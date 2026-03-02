package com.quizbattle.subject.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;


@Entity
@Table(
        name = "subjects",
        indexes = {
                @Index(name = "idx_subjects_code", columnList = "code", unique = true)
        }
)
public class Subject {

    @Id
    @GeneratedValue
    private UUID id;


    @Column(nullable = false, unique = true)
    private String code;


    @Column(nullable = false)
    private String name;


    @Column(name = "created_at", nullable = false)
    private Instant createdAt;



    public Subject() {
    }


    public Subject(String code, String name) {
        this.code = code;
        this.name = name;
        this.createdAt = Instant.now();
    }


    public UUID getId() {
        return id;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Instant getCreatedAt() {
        return createdAt;
    }

}