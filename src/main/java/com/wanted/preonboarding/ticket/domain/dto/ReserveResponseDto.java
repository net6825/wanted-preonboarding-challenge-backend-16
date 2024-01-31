package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ReserveResponseDto {
    private UUID performanceId;
    private int round;
    private String performanceName;
    private char line;
    private int seat;
    private String reservationName;
    private String reservationPhoneNumber;

    public ReserveResponseDto reserveResponseDto(Reservation reservation){
        return ReserveResponseDto.builder()
                .performanceId(reservation.getPerformanceId())
                .round(reservation.getRound())
//                .performanceName(reservation.get)
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .reservationName(reservation.getName())
                .reservationPhoneNumber(reservation.getPhoneNumber())
                .build();
    }
}
