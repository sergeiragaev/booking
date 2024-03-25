package ru.skillbox.booking.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.skillbox.booking.model.BookingEvent;


@Data
@Document(collection = "booking")
public class BookingStat {
    private String id;
    private BookingEvent event;
}
