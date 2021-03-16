package kz.edu.platform.course.service;

import kz.edu.platform.course.model.Lecture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LectureService{

    List<Lecture> findAll();
    Lecture save(Lecture lectureDto);
    Lecture findById(long id);
    Lecture update(long id, Lecture lectureDto);
    Lecture addAttachment(long id, MultipartFile attachment);

}
