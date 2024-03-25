package ru.skillbox.booking.service;

import ru.skillbox.booking.model.entity.Hotel;
import ru.skillbox.booking.web.filter.HotelFilter;

import java.util.List;

public interface HotelService {
    List<Hotel> findAll(HotelFilter filter);
    Hotel findById(Long id);
    Hotel save(Hotel hotel);
    Hotel update(Hotel hotel);
    void deleteById(Long id);
    void changeRating(int newMark, Long id);
}
