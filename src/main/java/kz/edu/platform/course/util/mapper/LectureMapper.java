package kz.edu.platform.course.util.mapper;

import kz.edu.platform.course.model.Lecture;
import kz.edu.platform.course.model.dto.LectureDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class LectureMapper {

    private static final ModelMapper mapper = Mapper.newMapper();

    public static LectureDto from(Lecture lecture) {
        LectureDto lectureDto = mapper.map(lecture, LectureDto.class);

        return lectureDto;
    }

    public static Page<LectureDto> from(Page<Lecture> page) {
        return page.map(LectureMapper::from);
    }

    public static Lecture from(LectureDto lectureDto) {
        Lecture lecture = mapper.map(lectureDto, Lecture.class);

        return lecture;
    }

}
