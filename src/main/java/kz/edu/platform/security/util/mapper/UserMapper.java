package kz.edu.platform.security.util.mapper;

import kz.edu.platform.common.model.Role;
import kz.edu.platform.common.model.User;
import kz.edu.platform.common.model.dto.UserDto;
import kz.edu.platform.course.util.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class UserMapper {

    private static final ModelMapper mapper = Mapper.newMapper();

    public static UserDto from(User user) {
//        UserDto userDto = mapper.map(user, UserDto.class);
        UserDto userDto = UserDto.builder()
                // BaseEntity fields
                .id(user.getId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .status(user.getStatus())
                //
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()))
                .build();

        return userDto;
    }

    public static Page<UserDto> from(Page<User> page) {
        return page.map(UserMapper::from);
    }

    public static User from(UserDto userDto) {
        User user = mapper.map(userDto, User.class);

        return user;
    }

}