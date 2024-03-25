package ru.skillbox.booking.model.kafka;

import lombok.Data;

import java.util.Date;

@Data
public class KafkaMessage {
    private String id;
    private Long userId;
    private Date dateIn;
    private Date dateOut;
}
