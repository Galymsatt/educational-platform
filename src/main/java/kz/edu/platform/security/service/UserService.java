package kz.edu.platform.security.service;

import kz.edu.platform.common.model.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto save(UserDto userDTO);
    Page<UserDto> getAllUsers(Pageable pageable, boolean admin, boolean teacher, boolean student);
    UserDto getByUsername(String username);
    UserDto getById(long id);


}
