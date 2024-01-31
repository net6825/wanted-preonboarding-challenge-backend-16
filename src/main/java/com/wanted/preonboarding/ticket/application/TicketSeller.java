package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private long totalAmount = 0L;

    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
            .stream()
            .map(PerformanceInfo::of)
            .toList();
    }

    public PerformanceInfo getPerformanceInfoDetail(String name) {
        return PerformanceInfo.of(performanceRepository.findByName(name));
    }
    public Reservation getReservation(ReservationSearchDto dto){
        Reservation reservation = reservationRepository.findByNameAndPhoneNumber(dto.getConsumerName(), dto.getConsumerPhoneNumber()).get();
        return reservation;
    }

    public ReservationResponseDto getReservationResponse(Reservation reservation){

        Performance findPerformance = performanceRepository.findById(reservation.getPerformanceId()).get();

        ReservationResponseDto response = ReservationResponseDto.builder()
                .round(reservation.getRound())
                .seat(reservation.getSeat())
                .line(reservation.getLine())
                .performanceId(reservation.getPerformanceId())
                .performanceName(findPerformance.getName())
                .consumerName(reservation.getName())
                .consumerPhoneNumber(reservation.getPhoneNumber())
                .build();

        return response;
    }

    public ResponseEnum reserve(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());
        Performance info = performanceRepository.findById(reserveInfo.getPerformanceId())
            .orElseThrow(EntityNotFoundException::new);
        String enableReserve = info.getIsReserve();

        if (enableReserve.equalsIgnoreCase("enable")) {
            // 1. 결제
            int price = info.getPrice();
            if(reserveInfo.getAmount() > price){
                reserveInfo.setAmount(reserveInfo.getAmount() - price);

                // 2. 예매 진행
                reservationRepository.save(Reservation.of(reserveInfo));
                return ResponseEnum.SUCCESS;
            }else {
                log.error("amount is less than the price");
                return ResponseEnum.ERROR;
            }
        } else {
            log.error("performance is not enable");
            return ResponseEnum.FAIL;
        }
    }

}
