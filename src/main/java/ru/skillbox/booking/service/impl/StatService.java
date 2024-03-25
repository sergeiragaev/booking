package ru.skillbox.booking.service.impl;

import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.booking.model.BookingEvent;
import ru.skillbox.booking.model.RegisterEvent;
import ru.skillbox.booking.model.entity.BookingStat;
import ru.skillbox.booking.model.entity.RegisterStat;
import ru.skillbox.booking.model.kafka.KafkaMessage;
import ru.skillbox.booking.repository.MongoBookingRepository;
import ru.skillbox.booking.repository.MongoUserRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class StatService {

    private final MongoBookingRepository mongoBookingRepository;
    private final MongoUserRepository mongoUserRepository;
    private final ResourceLoader resourceLoader;
    private static final String CSV_REGISTER_FILE_PATH = "./register.csv";
    private static final String CSV_BOOKING_FILE_PATH = "./booking.csv";
    public void addUser(KafkaMessage message) {
        RegisterEvent event = RegisterEvent.builder()
                .time(Instant.now())
                .userId(message.getUserId())
                .build();
        RegisterStat registerStat = new RegisterStat();
        registerStat.setId(UUID.randomUUID().toString());
        registerStat.setEvent(event);
        mongoUserRepository.save(registerStat);
    }

    public void addBooking(KafkaMessage message) {
        BookingEvent event = BookingEvent.builder()
                .time(Instant.now())
                .userId(message.getUserId())
                .dateIn(message.getDateIn())
                .dateOut(message.getDateOut())
                .build();
        BookingStat bookingStat = new BookingStat();
        bookingStat.setId(UUID.randomUUID().toString());
        bookingStat.setEvent(event);
        mongoBookingRepository.save(bookingStat);
    }

    public ResponseEntity<Resource> getRegisterStatFile() {
        List<String[]> data = new ArrayList<>(mongoUserRepository.findAll().stream().map(registerStat -> {
            String[] newRow = new String[2];
            if (registerStat.getEvent() != null) {
                newRow[0] = registerStat.getEvent().getTime().toString();
                newRow[1] = registerStat.getEvent().getUserId().toString();
            }
            return newRow;
        }).toList());

        File file = new File(CSV_REGISTER_FILE_PATH);
        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile);
            String[] header = { "Time", "User ID"};
            writer.writeNext(header);
            writer.writeAll(data);
            writer.close();

            Resource resource = resourceLoader.getResource("file:" + CSV_REGISTER_FILE_PATH);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                    CSV_REGISTER_FILE_PATH.substring(2));
            headers.setContentType(MediaType.TEXT_PLAIN);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public ResponseEntity<Resource>  getBookingStatFile() {
        List<String[]> data = new ArrayList<>(mongoBookingRepository.findAll().stream().map(bookingStat -> {
            String[] newRow = new String[4];
            if (bookingStat.getEvent() != null) {
                newRow[0] = bookingStat.getEvent().getTime().toString();
                newRow[1] = bookingStat.getEvent().getUserId().toString();
                newRow[2] = bookingStat.getEvent().getDateIn().toString();
                newRow[3] = bookingStat.getEvent().getDateOut().toString();
            }
            return newRow;
        }).toList());

        File file = new File(CSV_BOOKING_FILE_PATH);
        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile);
            String[] header = { "Time", "User ID", "Date in", "Date out"};
            writer.writeNext(header);
            writer.writeAll(data);
            writer.close();

            Resource resource = resourceLoader.getResource("file:" + CSV_BOOKING_FILE_PATH);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                    CSV_BOOKING_FILE_PATH.substring(2));
            headers.setContentType(MediaType.TEXT_PLAIN);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
