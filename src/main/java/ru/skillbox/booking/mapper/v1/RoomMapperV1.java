package ru.skillbox.booking.mapper.v1;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skillbox.booking.model.entity.Room;
import ru.skillbox.booking.web.dto.room.RoomResponse;
import ru.skillbox.booking.web.dto.room.UpsertRoomRequest;

import java.util.List;

@DecoratedWith(RoomMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapperV1 {
    Room requestToRoom(UpsertRoomRequest request);
    @Mapping(source = "roomId", target = "id")
    Room requestToRoom(Long roomId, UpsertRoomRequest request);
    RoomResponse roomToResponse(Room room);
    List<RoomResponse> roomListToResponseList(List<Room> rooms);
}
