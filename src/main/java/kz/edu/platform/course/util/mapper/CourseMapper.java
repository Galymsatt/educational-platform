package kz.edu.platform.course.util.mapper;

import kz.edu.platform.course.model.Course;
import kz.edu.platform.course.model.dto.CourseDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;


public class CourseMapper {

    private static final ModelMapper mapper = Mapper.newMapper();

    public static CourseDto from(Course course) {
        CourseDto courseDto = mapper.map(course, CourseDto.class);

        return courseDto;
    }

    public static Page<CourseDto> from(Page<Course> page) {
        return page.map(CourseMapper::from);
    }

    public static List<CourseDto> from(List<Course> page) {
        return page.stream().map(CourseMapper::from).collect(Collectors.toList());
    }

    public static Course from(CourseDto courseDto) {
        Course course = mapper.map(courseDto, Course.class);

        return course;
    }
}
