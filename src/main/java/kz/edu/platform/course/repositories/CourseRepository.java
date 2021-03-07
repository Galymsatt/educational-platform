package kz.edu.platform.course.repositories;

import kz.edu.platform.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAll();
    Optional<Course> findById(long id);
}
