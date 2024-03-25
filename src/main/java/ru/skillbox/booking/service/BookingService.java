package ru.skillbox.booking.service;

import ru.skillbox.booking.model.entity.Booking;

import java.util.List;

public interface BookingService {
    List<Booking> findAll();
    Booking findById(Long id);
    Booking save(Booking booking);
    void deleteById(Long id);
}
