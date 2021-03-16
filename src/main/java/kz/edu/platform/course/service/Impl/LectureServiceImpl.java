package kz.edu.platform.course.service.Impl;

import kz.edu.platform.course.model.Lecture;
import kz.edu.platform.course.repositories.LectureRepository;
import kz.edu.platform.course.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    @Override
    public List<Lecture> findAll() {
        return lectureRepository.findAll();
    }

    @Override
    public Lecture save(Lecture lectureDto) {
        return lectureRepository.save(lectureDto);
    }

    @Override
    public Lecture findById(long id) {

        Optional<Lecture> lectureOpt = lectureRepository.findById(id);

        if (!lectureOpt.isPresent()){
            throw new IllegalArgumentException("There is no lecture with id: " + id);
        }

        return lectureOpt.get();
    }

    @Override
    public Lecture update(long id, Lecture lectureDto) {

        Lecture lecture = findById(id);

        if (lectureDto.getName() != null && !lectureDto.getName().isEmpty()){
            lecture.setName(lectureDto.getName());
        }

        if (lectureDto.getDescription() != null && !lectureDto.getDescription().isEmpty()){
            lecture.setDescription(lectureDto.getDescription());
        }

        if (lectureDto.getContent() != null && !lectureDto.getContent().isEmpty()){
            lecture.setContent(lectureDto.getContent());
        }


        lecture.setUpdatedAt(new Date());

        return lectureRepository.save(lecture);
    }

    @Override
    public Lecture addAttachment(long id, MultipartFile attachment) {
        // Need to implement
        return null;
    }
}
