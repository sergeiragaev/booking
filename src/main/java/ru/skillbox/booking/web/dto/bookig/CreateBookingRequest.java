package ru.skillbox.booking.web.dto.bookig;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingRequest {
    @NotNull(message = "Дата заезда не может быть пустой!")
    private Date dateIn;

    @NotNull(message = "Дата выезда не может быть пустой!")
    private Date dateOut;

    @NotNull(message = "Необходимо указать ID комнаты!")
    private Long roomId;
}
