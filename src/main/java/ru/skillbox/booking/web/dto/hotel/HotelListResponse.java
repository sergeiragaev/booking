package ru.skillbox.booking.web.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelListResponse {
    private List<HotelResponse> hotels = new ArrayList<>();
}
