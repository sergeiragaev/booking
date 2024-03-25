package ru.skillbox.booking.web.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.booking.model.entity.Role;
import ru.skillbox.booking.model.entity.RoleType;
import ru.skillbox.booking.mapper.v1.UserMapperV1;
import ru.skillbox.booking.service.UserService;
import ru.skillbox.booking.web.dto.user.CreateUserRequest;
import ru.skillbox.booking.web.dto.user.UserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserControllerV1 {
    private final UserService databaseUserService;
    private final UserMapperV1 userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(userMapper.userToResponse(databaseUserService.findById(id)));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> create(@RequestParam @Valid RoleType roleType,
                                               @RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
          userMapper.userToResponse(
                  databaseUserService.createNewAccount(userMapper.requestToUser(request), Role.from(roleType))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        databaseUserService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
