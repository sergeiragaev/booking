package ru.skillbox.booking.web.dto.hotel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertHotelRequest {
    @NotBlank(message = "Необходимо заполнить название отеля!")
    private String name;

    @NotBlank(message = "Необходимо заполнить описание отеля!")
    private String title;

    @NotBlank(message = "Необходимо заполнить город, где расположен отель!")
    private String city;

    @NotBlank(message = "Необходимо заполнить адрес отеля!")
    private String address;

    @NotNull(message = "Необходимо заполнить расстояние от центра города!")
    @Positive(message = "Расстояние от центра города должно быть больше 0!")
    private float distance;
}
