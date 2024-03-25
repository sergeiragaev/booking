package ru.skillbox.booking.mapper.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skillbox.booking.model.entity.Booking;
import ru.skillbox.booking.service.RoomService;
import ru.skillbox.booking.service.UserService;
import ru.skillbox.booking.web.dto.bookig.CreateBookingRequest;

public abstract class BookingMapperDelegate implements BookingMapperV1 {
    @Autowired
    private RoomService databaseRoomService;
    @Autowired
    private UserService databaseUserService;

    @Override
    public Booking requestToBooking(CreateBookingRequest request, UserDetails userDetails) {

        return Booking.builder()
                .dateIn(request.getDateIn())
                .dateOut(request.getDateOut())
                .user(databaseUserService.findByUsername(userDetails.getUsername()))
                .room(databaseRoomService.findById(request.getRoomId()))
                .build();
    }

}
