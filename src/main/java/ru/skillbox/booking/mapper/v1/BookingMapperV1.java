package ru.skillbox.booking.mapper.v1;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skillbox.booking.model.entity.Booking;
import ru.skillbox.booking.web.dto.bookig.BookingResponse;
import ru.skillbox.booking.web.dto.bookig.CreateBookingRequest;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(BookingMapperDelegate.class)
public interface BookingMapperV1 {

    Booking requestToBooking(CreateBookingRequest request, UserDetails userDetails);

    BookingResponse bookingToResponse(Booking booking);

    List<BookingResponse> bookinListToResponseList(List<Booking> bookings);
}
