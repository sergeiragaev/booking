package ru.skillbox.booking.web.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.booking.web.dto.room.RoomResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {
    private Long id;
    private String name;
    private String title;
    private String city;
    private String address;
    private float distance;
    private float rating;
    private long numberOfRating;
    private List<RoomResponse> rooms;
}
