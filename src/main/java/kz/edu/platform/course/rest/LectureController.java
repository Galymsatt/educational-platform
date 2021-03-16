package kz.edu.platform.course.rest;

import io.swagger.annotations.ApiOperation;
import kz.edu.platform.course.model.Lecture;
import kz.edu.platform.course.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/lectures")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @ApiOperation("Save lecture")
    @PutMapping(value = "/save")
    public ResponseEntity<?> createCourse(@RequestBody Lecture lectureDto){
        return new ResponseEntity<>(lectureService.save(lectureDto), HttpStatus.CREATED);
    }

    @ApiOperation("Get all lectures")
    @GetMapping(value = "/")
    public ResponseEntity<?> getAllCourses(){
        return ResponseEntity.ok(lectureService.findAll());
    }

    @ApiOperation("Get lecture by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(lectureService.findById(id));
    }

    @ApiOperation("Update lecture")
    @PostMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id,
                                    @RequestBody Lecture lectureDto){
        return ResponseEntity.ok(lectureService.update(id, lectureDto));
    }

    @ApiOperation("Add attachment to lecture")
    @PostMapping(value = "/{id}/attachment")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id,
                                    @RequestParam MultipartFile attachment){
        return ResponseEntity.ok(lectureService.addAttachment(id, attachment));
    }

}
