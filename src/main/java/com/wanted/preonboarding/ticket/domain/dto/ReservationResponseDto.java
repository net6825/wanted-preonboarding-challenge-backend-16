package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ReservationResponseDto {
    private int round;
    private String performanceName;
    private int seat;
    private char line;
    private String consumerName;
    private String consumerPhoneNumber;
    private UUID performanceId;
}
