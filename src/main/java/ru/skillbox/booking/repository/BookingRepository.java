package ru.skillbox.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.booking.model.entity.Booking;

import java.util.Date;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "SELECT COUNT(*) FROM unavailable_dates WHERE room_id = :roomId AND " +
            "date BETWEEN :dateIn AND :dateOut", nativeQuery = true)
    int unavailableDateCount(Long roomId, Date dateIn, Date dateOut);

    @Query(value = "INSERT INTO unavailable_dates(room_id, date) VALUES (:roomId, :date) RETURNING id", nativeQuery = true)
    long insertIntoUnavailableDate(Long roomId, Date date);

    @Query(value = "DELETE FROM unavailable_dates WHERE room_id = :roomId AND " +
            "date BETWEEN :dateIn AND :dateOut RETURNING *", nativeQuery = true)
    long deleteFromUnavailableDate(Long roomId, Date dateIn, Date dateOut);

}
