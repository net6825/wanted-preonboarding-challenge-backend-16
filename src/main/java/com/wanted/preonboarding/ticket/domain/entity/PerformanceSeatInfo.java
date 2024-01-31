package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceSeatInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID performanceId;

    @Column(unique = true)
    private int round;
    private char line;
    private int seat;

    @Column(columnDefinition = "varchar(255) default 'enable'", nullable = false)
    private String isReserve;

    @Column(nullable = false)
    private int gate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
