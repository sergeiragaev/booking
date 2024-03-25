package ru.skillbox.booking.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;


@Data
@Builder
public class RegisterEvent {
    private Instant time;
    private Long userId;
}
