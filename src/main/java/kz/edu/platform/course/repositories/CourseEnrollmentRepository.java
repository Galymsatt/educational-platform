package kz.edu.platform.course.repositories;

import kz.edu.platform.common.model.User;
import kz.edu.platform.course.model.CourseEnrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {

    Page<CourseEnrollment> findAllByStudentsContains(Pageable pageable, User student);
    List<CourseEnrollment> findAllByStudentsContains(User student);
    Page<CourseEnrollment> findAllByTeacher(Pageable pageable, User teacher);
    List<CourseEnrollment> findAllByTeacher(User teacher);

}
