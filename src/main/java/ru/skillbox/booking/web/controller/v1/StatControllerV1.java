package ru.skillbox.booking.web.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.booking.service.impl.StatService;


@RestController
@RequestMapping("/api/v1/stat")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class StatControllerV1 {

    private final StatService statService;

    @GetMapping("/register")
    public ResponseEntity<Resource> getRegisterFile() {
        return statService.getRegisterStatFile();
    }

    @GetMapping("/booking")
    public ResponseEntity<Resource> getBookingFile() {
        return statService.getBookingStatFile();
    }
}
