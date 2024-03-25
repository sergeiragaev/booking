package ru.skillbox.booking.web.dto.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertRoomRequest {
    @NotNull(message = "ID отеля должен быть указан!")
    @Positive(message = "ID отеля должен быть больше 0!")
    private Long hotelId;

    @NotBlank(message = "Необходимо заполнить название комнаты!")
    private String name;

    @NotBlank(message = "Необходимо заполнить описание комнаты!")
    private String description;

    @NotBlank(message = "Необходимо указать номер комнаты!")
    private String number;

    @NotNull(message = "Необходимо указать цену!")
    @Positive(message = "Цена должна быть больше 0!")
    private BigDecimal price;

    @NotNull(message = "Необходимо указать максимальное количество людей, которое можно разместить в комнате!")
    @Positive(message = "Количество людей должно быть больше 0!")
    private byte maxCapacity;
}
