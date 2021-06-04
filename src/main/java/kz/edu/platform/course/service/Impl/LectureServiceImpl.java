package kz.edu.platform.course.service.Impl;

import kz.edu.platform.common.model.UserContext;
import kz.edu.platform.course.model.Lecture;
import kz.edu.platform.course.model.dto.LectureDto;
import kz.edu.platform.course.repositories.LectureRepository;
import kz.edu.platform.course.service.LectureService;
import kz.edu.platform.course.util.mapper.LectureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<LectureDto> findAll(Pageable pageable, UserContext userContext) {
        return LectureMapper.from(lectureRepository.findAll(pageable));
    }

    @Override
    public LectureDto save(LectureDto lectureDto) {
        Lecture lecture = LectureMapper.from(lectureDto);

        return LectureMapper.from(lectureRepository.save(lecture));
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
    public LectureDto getLectureById(long id) {
        return LectureMapper.from(findById(id));
    }

    @Override
    public LectureDto update(long id, Lecture lectureDto) {

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

        return LectureMapper.from(lectureRepository.save(lecture));
    }

    @Override
    public LectureDto addAttachment(long id, MultipartFile attachment) {
        // Need to implement
        return null;
    }
}
