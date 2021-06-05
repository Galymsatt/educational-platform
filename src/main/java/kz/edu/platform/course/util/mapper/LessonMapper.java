package kz.edu.platform.course.util.mapper;

import kz.edu.platform.course.model.Lesson;
import kz.edu.platform.course.model.dto.LessonDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class LessonMapper {

    private static final ModelMapper mapper = Mapper.newMapper();

    public static LessonDto from(Lesson lesson) {
        LessonDto lessonDto = mapper.map(lesson, LessonDto.class);

        return lessonDto;
    }

    public static Page<LessonDto> from(Page<Lesson> page) {
        return page.map(LessonMapper::from);
    }

    public static Lesson from(LessonDto lessonDto) {
        Lesson lesson = mapper.map(lessonDto, Lesson.class);

        return lesson;
    }

}