package ru.skillbox.booking.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
@Builder
public class BookingEvent {
    private Instant time;
    private Long userId;
    private Date dateIn;
    private Date dateOut;
}
