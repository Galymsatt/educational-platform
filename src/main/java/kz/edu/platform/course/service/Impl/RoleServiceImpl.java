package kz.edu.platform.course.service.Impl;

import kz.edu.platform.common.model.Role;
import kz.edu.platform.course.service.RoleService;
import kz.edu.platform.security.repository.RoleRepositiry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepositiry roleRepositiry;

    public Role getByName(String name){
        return roleRepositiry.findRoleByName(name);
    }

}
