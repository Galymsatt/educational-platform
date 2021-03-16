package kz.edu.platform.course.repositories;

import kz.edu.platform.course.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<Lecture , Long> {

    List<Lecture> findAll();
    Optional<Lecture> findById(long id);

}
