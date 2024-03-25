package ru.skillbox.booking.web.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.booking.mapper.v1.RoomMapperV1;
import ru.skillbox.booking.model.entity.Room;
import ru.skillbox.booking.service.RoomService;
import ru.skillbox.booking.web.dto.room.RoomResponse;
import ru.skillbox.booking.web.dto.room.UpsertRoomRequest;
import ru.skillbox.booking.web.filter.RoomFilter;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomControllerV1 {

    private final RoomService roomService;
    private final RoomMapperV1 roomMapper;
    @GetMapping
    public ResponseEntity<List<RoomResponse>> findAll(RoomFilter filter) {
        return ResponseEntity.ok(roomMapper.roomListToResponseList(roomService.findAll(filter)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RoomResponse> create(@Valid @RequestBody UpsertRoomRequest request) {
        Room room = roomService.save(roomMapper.requestToRoom(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                roomMapper.roomToResponse(room)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id, @Valid @RequestBody UpsertRoomRequest request) {
        Room updatedRoom = roomService.update(roomMapper.requestToRoom(id, request));

        return ResponseEntity.ok(roomMapper.roomToResponse(updatedRoom));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
