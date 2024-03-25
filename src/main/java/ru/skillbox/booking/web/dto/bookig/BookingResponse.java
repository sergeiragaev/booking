package ru.skillbox.booking.web.dto.bookig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.booking.web.dto.room.RoomResponse;
import ru.skillbox.booking.web.dto.user.UserResponse;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long id;
    private Date dateIn;
    private Date dateOut;
    private RoomResponse room;
    private UserResponse user;
}
