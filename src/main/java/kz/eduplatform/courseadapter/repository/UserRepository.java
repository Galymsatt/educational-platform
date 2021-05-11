package kz.eduplatform.courseadapter.repository;

import kz.eduplatform.courseadapter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
