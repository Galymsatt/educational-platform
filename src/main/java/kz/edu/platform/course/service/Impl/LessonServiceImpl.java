package kz.edu.platform.course.service.Impl;

import kz.edu.platform.course.model.Lesson;
import kz.edu.platform.course.model.dto.LessonDto;
import kz.edu.platform.course.repositories.LessonRepository;
import kz.edu.platform.course.util.mapper.LessonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl { //дописать интерфес серивис

    private final LessonRepository repository;

    public LessonDto save(LessonDto dto) {
        Lesson lesson = LessonMapper.from(dto);

        return LessonMapper.from(repository.save(lesson));
    }

}
