package kz.edu.platform.security.repository;

import kz.edu.platform.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
    User findByHashCode(String hashCode);
}
