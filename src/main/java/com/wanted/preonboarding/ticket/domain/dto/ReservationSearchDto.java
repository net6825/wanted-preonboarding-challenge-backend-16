package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationSearchDto {
    private String consumerName;
    private String consumerPhoneNumber;
}
