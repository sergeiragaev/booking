package ru.skillbox.booking.web.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RoomFilter extends PageFilter {

    private Long roomId;
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private byte capacity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateIn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOut;

    private Long hotelId;
}
