package kz.edu.platform.security.repository;

import kz.edu.platform.common.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositiry extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
