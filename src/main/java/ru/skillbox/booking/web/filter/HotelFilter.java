package ru.skillbox.booking.web.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class HotelFilter extends PageFilter {

    private Long id;
    private String name;
    private String title;
    private String city;
    private String address;
    private float distanceFrom;
    private float distanceTo;
    private float ratingFrom;
    private float ratingTo;
    private long numberOfRatingFrom;
    private long numberOfRatingTo;
}
