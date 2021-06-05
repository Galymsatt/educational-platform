package kz.edu.platform.security.repository;

import kz.edu.platform.common.model.Role;
import kz.edu.platform.common.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
    User findByHashCode(String hashCode);
    Page<User> findAllByRolesIn(Pageable pageable, List<Role> role);
}
