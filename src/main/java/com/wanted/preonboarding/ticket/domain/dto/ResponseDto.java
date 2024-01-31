package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto {
    private ResponseEnum responseCode;
    private String message;
    private Object data;
}
