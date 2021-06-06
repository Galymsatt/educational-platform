package kz.edu.platform.course.rest;

import io.swagger.annotations.ApiOperation;
import kz.edu.platform.common.model.UserContext;
import kz.edu.platform.course.model.dto.CourseEnrollmentDto;
import kz.edu.platform.course.service.CourseEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/courseEnrollments")
public class CourseEnrollmentController {

    private final CourseEnrollmentService service;

    @ApiOperation("Save CourseEnrollment")
    @PostMapping(value = "/save")
    public ResponseEntity<?> createCourseEnrollment(@RequestBody CourseEnrollmentDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @ApiOperation("Get all CourseEnrollments")
    @GetMapping(value = "")
    public ResponseEntity<?> getAllCourseEnrollment(@PageableDefault Pageable pageable,
                                           UserContext userContext){
//        return ResponseEntity.ok(service.findAll(pageable, userContext));
        return ResponseEntity.ok(service.findAll(userContext));
    }

    @ApiOperation("Get CourseEnrollment by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCourseEnrollmentById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(service.getCourseEnrollmentById(id));
    }

    @ApiOperation("Update CourseEnrollment")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id,
                                    @RequestBody CourseEnrollmentDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @ApiOperation("Add Student to CourseEnrollment")
    @PostMapping(value = "/addStudent/{cEnrltId}/{studId}")
    public ResponseEntity<?> addLectureToCourse(@PathVariable (name = "cEnrltId") Long cEnrltId,
                                                @PathVariable (name = "studId") Long studId){
        return ResponseEntity.ok(service.addStudentToCourseEnrollment(cEnrltId, studId));
    }

}
