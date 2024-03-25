package ru.skillbox.booking.mapper.v1;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.skillbox.booking.model.entity.User;
import ru.skillbox.booking.web.dto.user.CreateUserRequest;
import ru.skillbox.booking.web.dto.user.UserResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapperV1 {
    User requestToUser(CreateUserRequest request);
    UserResponse userToResponse(User user);
}
