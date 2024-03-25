package ru.skillbox.booking.service.impl;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.booking.model.entity.UnavailableDate;
import ru.skillbox.booking.exception.EntityNotFoundException;
import ru.skillbox.booking.model.entity.Room;
import ru.skillbox.booking.repository.RoomRepository;
import ru.skillbox.booking.service.RoomService;
import ru.skillbox.booking.utils.BeanUtils;
import ru.skillbox.booking.web.filter.RoomFilter;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseRoomService implements RoomService {

    private final RoomRepository roomRepository;
    @Override
    public List<Room> findAll(RoomFilter filter) {

        if (filter.getPageNumber() == null) filter.setPageNumber(0);

        if (filter.getPageSize() == null) filter.setPageSize(Integer.MAX_VALUE);

        return roomRepository.findAll(FilterSpecification.findByFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "Комната с ID {0} не найдена!", id)));
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room update(Room room) {
        Room existedRoom = findById(room.getId());
        BeanUtils.copyNonNullProperties(room, existedRoom);
        return roomRepository.save(existedRoom);
    }

    @Override
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    private static class FilterSpecification {
        private static Specification<Room> findByFilter(final RoomFilter roomFilter) {
            return (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (roomFilter.getRoomId() != null) {
                    predicates.add(cb.equal(root.get("id"), roomFilter.getRoomId()));
                }
                if (roomFilter.getName() != null && !roomFilter.getName().isEmpty()) {
                    predicates.add(cb.equal(root.get("name"), roomFilter.getName()));
                }
                if (roomFilter.getHotelId() != null) {
                    predicates.add(cb.equal(root.get("hotelId"), roomFilter.getHotelId()));
                }
                if (roomFilter.getMinPrice() != null || roomFilter.getMaxPrice() != null) {
                    BigDecimal minimum = roomFilter.getMinPrice();
                    BigDecimal maximum = roomFilter.getMaxPrice();
                    if (roomFilter.getMinPrice() == null) minimum = BigDecimal.valueOf(0);
                    if (roomFilter.getMaxPrice() == null) maximum = BigDecimal.valueOf(Long.MAX_VALUE);
                    predicates.add(cb.between(root.get("price"), minimum, maximum));
                }
                if (roomFilter.getCapacity() >  0) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("maxCapacity"), roomFilter.getCapacity()));
                }
                if (roomFilter.getDateIn() != null && roomFilter.getDateOut() != null) {
                    if (roomFilter.getDateIn().before(roomFilter.getDateOut())) {
                        List<java.sql.Date> dates = new ArrayList<>();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        for (Date currDate = new Date(roomFilter.getDateIn().getTime());
                             currDate.before(new Date(roomFilter.getDateOut().getTime())); ) {
                            dates.add(java.sql.Date.valueOf(LocalDate.parse(dateFormat.format(currDate))));
                            currDate.setTime(currDate.getTime() + 1000 * 60 * 60 * 24);
                        }

                        Subquery<Room> subquery = query.subquery(Room.class);
                        Root<Room> subqueryRoot = subquery.from(Room.class);
                        Join<Room, UnavailableDate> unavailableDates = subqueryRoot.join("unavailableDates");
                        subquery.select(subqueryRoot);
                        Predicate roomIdPredicate = cb.equal(subqueryRoot.get("id"), root.<String> get("id"));
                        subquery.select(subqueryRoot).where(roomIdPredicate, unavailableDates.get("date").in(dates));
                        predicates.add(cb.exists(subquery).not());
                    }
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            };
        }
    }
}
