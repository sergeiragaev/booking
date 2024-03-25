package ru.skillbox.booking.mapper.v1;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skillbox.booking.model.entity.Hotel;
import ru.skillbox.booking.web.dto.hotel.HotelListResponse;
import ru.skillbox.booking.web.dto.hotel.HotelResponse;
import ru.skillbox.booking.web.dto.hotel.UpsertHotelRequest;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {RoomMapperV1.class})
public interface HotelMapperV1 {
    Hotel requestToHotel(UpsertHotelRequest request);
    @Mapping(source = "hotelId", target = "id")
    Hotel requestToHotel(Long hotelId, UpsertHotelRequest request);
    HotelResponse hotelToResponse(Hotel hotel);
    List<HotelResponse> hotelListToResponseList(List<Hotel> hotels);
    default HotelListResponse hotelListToHotelListResponse(List<Hotel> hotels) {
        HotelListResponse response = new HotelListResponse();
        response.setHotels(hotelListToResponseList(hotels));

        return response;
    }
}
