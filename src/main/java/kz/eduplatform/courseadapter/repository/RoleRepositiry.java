package kz.eduplatform.courseadapter.repository;

import kz.eduplatform.courseadapter.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositiry extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
