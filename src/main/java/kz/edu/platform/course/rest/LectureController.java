package kz.edu.platform.course.rest;

import io.swagger.annotations.ApiOperation;
import kz.edu.platform.common.model.UserContext;
import kz.edu.platform.course.model.Lecture;
import kz.edu.platform.course.model.dto.LectureDto;
import kz.edu.platform.course.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/lectures")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @ApiOperation("Save lecture")
    @PutMapping(value = "/save")
    public ResponseEntity<?> createLecture(@RequestBody LectureDto lectureDto){
        return new ResponseEntity<>(lectureService.save(lectureDto), HttpStatus.CREATED);
    }

    @ApiOperation("Get all lectures")
    @GetMapping(value = "")
    public ResponseEntity<?> getAllLectures(@PageableDefault Pageable pageable,
                                            UserContext userContext){
        return ResponseEntity.ok(lectureService.findAll(pageable, userContext));
    }

    @ApiOperation("Get lecture by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getLectureById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(lectureService.getLectureById(id));
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
