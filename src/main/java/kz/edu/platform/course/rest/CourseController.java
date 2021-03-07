package kz.edu.platform.course.rest;

import io.swagger.annotations.ApiOperation;
import kz.edu.platform.course.model.Course;
import kz.edu.platform.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("Save course")
    @PutMapping(value = "/save")
    public ResponseEntity<Course> createCourse(@RequestBody Course course){
        return new ResponseEntity<>(courseService.createCourse(course), HttpStatus.CREATED);
    }

    @ApiOperation("Get all courses")
    @GetMapping(value = "/")
    public ResponseEntity<?> getAllCourses(){
        return ResponseEntity.ok(courseService.findAll());
    }

    @ApiOperation("Get course by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(courseService.findById(id));
    }

    @ApiOperation("Update course")
    @PostMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id,
                                    @RequestBody Course course){
        return ResponseEntity.ok(courseService.update(id, course));
    }


}
