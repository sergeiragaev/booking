package ru.skillbox.booking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.skillbox.booking.model.entity.BookingStat;

public interface MongoBookingRepository extends MongoRepository<BookingStat, String> {
}
