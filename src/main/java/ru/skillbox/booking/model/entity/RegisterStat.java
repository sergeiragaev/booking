package ru.skillbox.booking.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.skillbox.booking.model.RegisterEvent;

@Data
@Document(collection = "register")
public class RegisterStat {
    private String id;
    private RegisterEvent event;
}
