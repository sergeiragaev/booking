package ru.skillbox.booking.mapper.v1;

import org.springframework.beans.factory.annotation.Autowired;
import ru.skillbox.booking.model.entity.Room;
import ru.skillbox.booking.service.HotelService;
import ru.skillbox.booking.web.dto.room.UpsertRoomRequest;

public abstract class RoomMapperDelegate implements RoomMapperV1 {
    @Autowired
    private HotelService databaseHotelService;

    @Override
    public Room requestToRoom(UpsertRoomRequest request) {

        return Room.builder()
                .name(request.getName())
                .description(request.getDescription())
                .number(request.getNumber())
                .price(request.getPrice())
                .maxCapacity(request.getMaxCapacity())
                .hotel(databaseHotelService.findById(request.getHotelId()))
                .build();
    }

    @Override
    public Room requestToRoom(Long roomId, UpsertRoomRequest request) {
        Room room = requestToRoom(request);
        room.setId(roomId);

        return room;
    }
}
