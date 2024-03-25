package ru.skillbox.booking.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.booking.exception.EntityNotFoundException;
import ru.skillbox.booking.model.entity.Hotel;
import ru.skillbox.booking.repository.HotelRepository;
import ru.skillbox.booking.service.HotelService;
import ru.skillbox.booking.utils.BeanUtils;
import ru.skillbox.booking.web.filter.HotelFilter;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseHotelService implements HotelService {

    private final HotelRepository hotelRepository;
    @Override
    public List<Hotel> findAll(HotelFilter filter) {

        if (filter.getPageNumber() == null) filter.setPageNumber(0);

        if (filter.getPageSize() == null) filter.setPageSize(Integer.MAX_VALUE);

        return hotelRepository.findAll(FilterSpecification.findByFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "Отель с ID {0} не найден!", id)));
    }

    @Override
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel update(Hotel hotel) {
        Hotel existedHotel = findById(hotel.getId());
        BeanUtils.copyNonNullProperties(hotel, existedHotel);
        return hotelRepository.save(existedHotel);
    }

    @Override
    public void deleteById(Long id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public void changeRating(int newMark, Long id) {

        if (newMark < 1 || newMark > 5) {
            throw new RuntimeException("Рейтинг должен быть в диапазоне от 1 до 5!");
        }

        Hotel existedHotel = findById(id);
        double totalRating = existedHotel.getRating() * existedHotel.getNumberOfRating();
        totalRating = totalRating + newMark;
        existedHotel.setNumberOfRating(existedHotel.getNumberOfRating() + 1);
        DecimalFormat df = new DecimalFormat("#.#");
        String stringRating = df.format(totalRating / existedHotel.getNumberOfRating());
        float newRating = Float.parseFloat(stringRating.replace(",", "."));
        existedHotel.setRating(newRating);

        hotelRepository.save(existedHotel);
    }

    private static class FilterSpecification {
        private static Specification<Hotel> findByFilter(final HotelFilter hotelFilter) {
            return (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (hotelFilter.getId() != null) {
                    predicates.add(cb.equal(root.get("id"), hotelFilter.getId()));
                }
                if (hotelFilter.getName() != null && !hotelFilter.getName().isEmpty()) {
                    predicates.add(cb.equal(root.get("name"), hotelFilter.getName()));
                }
                if (hotelFilter.getTitle() != null && !hotelFilter.getTitle().isEmpty()) {
                    predicates.add(cb.equal(root.get("title"), hotelFilter.getTitle()));
                }
                if (hotelFilter.getCity() != null && !hotelFilter.getCity().isEmpty()) {
                    predicates.add(cb.equal(root.get("city"), hotelFilter.getCity()));
                }
                if (hotelFilter.getAddress() != null && !hotelFilter.getAddress().isEmpty()) {
                    predicates.add(cb.equal(root.get("address"), hotelFilter.getAddress()));
                }
                if (hotelFilter.getDistanceFrom() > 0 || hotelFilter.getDistanceTo() > 0) {
                    float newTo = Float.MAX_VALUE;
                    if (hotelFilter.getDistanceTo() > 0) newTo = hotelFilter.getDistanceTo();
                    predicates.add(cb.between(root.get("distance"), hotelFilter.getDistanceFrom(), newTo));
                }
                if (hotelFilter.getRatingFrom() > 0 || hotelFilter.getRatingTo() > 0) {
                    float newTo = Float.MAX_VALUE;
                    if (hotelFilter.getRatingTo() > 0) newTo = hotelFilter.getRatingTo();
                    predicates.add(cb.between(root.get("rating"), hotelFilter.getRatingFrom(), newTo));
                }
                if (hotelFilter.getNumberOfRatingFrom() > 0 || hotelFilter.getNumberOfRatingTo() > 0) {
                    long newTo = Long.MAX_VALUE;
                    if (hotelFilter.getNumberOfRatingTo() > 0) newTo = hotelFilter.getNumberOfRatingTo();
                    predicates.add(cb.between(root.get("numberOfRating"), hotelFilter.getNumberOfRatingFrom(), newTo));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            };
        }
    }
}
