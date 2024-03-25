package ru.skillbox.booking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.skillbox.booking.model.entity.RegisterStat;

public interface MongoUserRepository extends MongoRepository<RegisterStat, String> {
}
