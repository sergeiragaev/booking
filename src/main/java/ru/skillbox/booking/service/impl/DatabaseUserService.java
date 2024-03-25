package ru.skillbox.booking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.booking.model.kafka.KafkaMessage;
import ru.skillbox.booking.model.entity.Role;
import ru.skillbox.booking.exception.AlreadyExistsException;
import ru.skillbox.booking.exception.EntityNotFoundException;
import ru.skillbox.booking.model.entity.User;
import ru.skillbox.booking.repository.UserRepository;
import ru.skillbox.booking.service.UserService;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DatabaseUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Value("${app.kafka.kafkaMessageUserTopic}")
    private String topicName;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "Пользователь с ID: {0} не найден!", id)));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "Пользователь с именем: {0} не найден!", username)));
    }

    @Override
    public User createNewAccount(User user, Role role) {
        if (userRepository.existsByUsernameAndEmail(user.getUsername(), user.getEmail())) {
            throw new AlreadyExistsException("Пользователь с такими учетными данными уже существует!");
        }
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);

        User newUser = userRepository.save(user);

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setId(UUID.randomUUID().toString());
        kafkaMessage.setUserId(newUser.getId());
        kafkaTemplate.send(topicName, kafkaMessage);

        return newUser;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
