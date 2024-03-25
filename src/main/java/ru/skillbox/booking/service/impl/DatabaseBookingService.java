package ru.skillbox.booking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.skillbox.booking.model.entity.Booking;
import ru.skillbox.booking.exception.AlreadyExistsException;
import ru.skillbox.booking.exception.EntityNotFoundException;
import ru.skillbox.booking.model.kafka.KafkaMessage;
import ru.skillbox.booking.repository.BookingRepository;
import ru.skillbox.booking.service.BookingService;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DatabaseBookingService implements BookingService {

    private final BookingRepository bookingRepository;

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Value("${app.kafka.kafkaMessageBookingTopic}")
    private String topicName;

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Бронирование с ID {0} не найдено!", id)));
    }

    @Override
    public Booking save(Booking booking) {

        if (booking.getDateIn().equals(booking.getDateOut()) ||
                booking.getDateIn().after(booking.getDateOut())) {
            throw new AlreadyExistsException("Дата заезда должна быть раньше даты выезда!");
        }

        if (bookingRepository.unavailableDateCount(
                booking.getRoom().getId(), booking.getDateIn(), booking.getDateOut()) > 0) {
            throw new AlreadyExistsException("Невозможно забронировать комнату на указанные даты!");
        }

        for (Date currentDate = new Date(booking.getDateIn().getTime());
             currentDate.before(booking.getDateOut());
             currentDate.setTime(currentDate.getTime() + 1000 * 60 * 60 * 24)) {
            bookingRepository.insertIntoUnavailableDate(booking.getRoom().getId(), currentDate);
        }


        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setId(UUID.randomUUID().toString());
        kafkaMessage.setUserId(booking.getUser().getId());
        kafkaMessage.setDateIn(booking.getDateIn());
        kafkaMessage.setDateOut(booking.getDateOut());
        kafkaTemplate.send(topicName, kafkaMessage);

        return bookingRepository.save(booking);
    }

    @Override
    public void deleteById(Long id) {
        Booking booking = findById(id);
        bookingRepository.deleteFromUnavailableDate(booking.getRoom().getId(), booking.getDateIn(), booking.getDateOut());

        bookingRepository.deleteById(id);
    }
}
