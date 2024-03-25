package ru.skillbox.booking.web.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.booking.mapper.v1.HotelMapperV1;
import ru.skillbox.booking.model.entity.Hotel;
import ru.skillbox.booking.service.HotelService;
import ru.skillbox.booking.web.dto.hotel.HotelResponse;
import ru.skillbox.booking.web.dto.hotel.UpsertHotelRequest;
import ru.skillbox.booking.web.filter.HotelFilter;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelControllerV1 {

    private final HotelService hotelService;
    private final HotelMapperV1 hotelMapper;
    @GetMapping
    public ResponseEntity<List<HotelResponse>> findAll(@Valid HotelFilter filter) {
        return ResponseEntity.ok(hotelMapper.hotelListToResponseList(hotelService.findAll(filter)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HotelResponse> create(@Valid @RequestBody UpsertHotelRequest request) {
        Hotel hotel = hotelService.save(hotelMapper.requestToHotel(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                hotelMapper.hotelToResponse(hotel)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> update(@PathVariable Long id, @Valid @RequestBody UpsertHotelRequest request) {
        Hotel updatedHotel = hotelService.update(hotelMapper.requestToHotel(id, request));

        return ResponseEntity.ok(hotelMapper.hotelToResponse(updatedHotel));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hotelService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/rating")
    public ResponseEntity<Void> changeRating(@RequestParam int newMark, @RequestParam Long id) {
        hotelService.changeRating(newMark, id);

        return ResponseEntity.noContent().build();
    }
}
