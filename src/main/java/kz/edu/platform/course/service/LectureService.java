package kz.edu.platform.course.service;

import kz.edu.platform.common.model.UserContext;
import kz.edu.platform.course.model.Lecture;
import kz.edu.platform.course.model.dto.LectureDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LectureService{

    Page<LectureDto> findAll(Pageable pageable, UserContext userContext);
    LectureDto save(LectureDto lectureDto);
    Lecture findById(long id);
    LectureDto getLectureById(long id);
    LectureDto update(long id, Lecture lectureDto);
    LectureDto addAttachment(long id, MultipartFile attachment);

}
