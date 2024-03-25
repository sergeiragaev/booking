package ru.skillbox.booking.web.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.booking.mapper.v1.BookingMapperV1;
import ru.skillbox.booking.service.BookingService;
import ru.skillbox.booking.web.dto.bookig.BookingResponse;
import ru.skillbox.booking.web.dto.bookig.CreateBookingRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
public class BookingControllerV1 {
    private final BookingService databaseBookingService;
    private final BookingMapperV1 bookingMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<BookingResponse>> findAll() {
        return ResponseEntity.ok(
                bookingMapper.bookinListToResponseList(databaseBookingService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                bookingMapper.bookingToResponse(databaseBookingService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> create(@RequestBody @Valid CreateBookingRequest request,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                bookingMapper.bookingToResponse(
                        databaseBookingService.save(
                                bookingMapper.requestToBooking(request, userDetails)))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        databaseBookingService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
