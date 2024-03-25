package ru.skillbox.booking.service;

import ru.skillbox.booking.model.entity.Room;
import ru.skillbox.booking.web.filter.RoomFilter;

import java.util.List;

public interface RoomService {
    List<Room> findAll(RoomFilter filter);
    Room findById(Long id);
    Room save(Room room);
    Room update(Room room);
    void deleteById(Long id);
}
