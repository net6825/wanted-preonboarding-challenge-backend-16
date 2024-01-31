package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {

    private final TicketSeller ticketSeller;
    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;

    @PostMapping("/")
    public ResponseEntity reservation(@RequestBody ReserveInfo info) {
        System.out.println("reservation");

        ResponseDto responseDto = new ResponseDto();

//        ReserveInfo reserveInfo = reserve(info);

        ResponseEnum reserveResult = ticketSeller.reserve(info);
        if(reserveResult.equals(ResponseEnum.SUCCESS)){
            Optional<Reservation> reservation = reservationRepository.findByNameAndPhoneNumber(info.getReservationName(), info.getReservationPhoneNumber());
            responseDto.setResponseCode(ResponseEnum.SUCCESS);
            responseDto.setMessage("reservation success");
            responseDto.setData(reservation);

            return ResponseEntity.ok(responseDto);
        }else if(reserveResult.equals(ResponseEnum.ERROR)){
            responseDto.setResponseCode(ResponseEnum.ERROR);
            responseDto.setMessage("amount is less than the price");
            return new ResponseEntity(responseDto, HttpStatus.PAYMENT_REQUIRED);
        }else {
            responseDto.setResponseCode(ResponseEnum.FAIL);
            responseDto.setMessage("performance is not enable");
            return new ResponseEntity(responseDto, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity isReservationExist(@RequestBody ReservationSearchDto searchDto){
        ResponseDto dto = new ResponseDto();

        Reservation reservation = ticketSeller.getReservation(searchDto);

        if(reservation!= null){
            ReservationResponseDto searchReservation = ticketSeller.getReservationResponse(reservation);
            dto.setResponseCode(ResponseEnum.SUCCESS);
            dto.setMessage("check is success");
            dto.setData(searchReservation);
            return ResponseEntity.ok(dto);
        }else{
            dto.setMessage("check is fail");
            dto.setResponseCode(ResponseEnum.FAIL);
            return new ResponseEntity(dto, HttpStatus.NOT_FOUND);
        }
    }

    private ReserveInfo reserve(ReserveInfo info) {
        ReserveInfo reserveInfo = ReserveInfo.builder()
                .performanceId(info.getPerformanceId())
                .reservationName(info.getReservationName())
                .reservationPhoneNumber(info.getReservationPhoneNumber())
                .reservationStatus(info.getReservationStatus())
                .amount(info.getAmount())
                .round(info.getRound())
                .line(info.getLine())
                .seat(info.getSeat())
                .build();
        return reserveInfo;
    }
}
