package kz.edu.platform.security.security.controller;

import io.swagger.annotations.ApiOperation;
import kz.edu.platform.common.model.UserContext;
import kz.edu.platform.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private final UserService userService;

    @ApiOperation("Get all users")
    @GetMapping(value = "")
    public ResponseEntity<?> getAllUsers(@PageableDefault Pageable pageable,
                                         @RequestParam(name = "admin", defaultValue = "false") boolean admin,
                                         @RequestParam(name = "teacher", defaultValue = "false") boolean teacher,
                                         @RequestParam(name = "student", defaultValue = "false") boolean student,
                                         UserContext userContext){
        return ResponseEntity.ok(userService.getAllUsers(pageable, admin, teacher, student));
    }

    @ApiOperation("Get user by userName")
    @GetMapping(value = "/{userName}")
    public ResponseEntity<?> getByUserName(@PathVariable(name = "userName") String userName){
        return ResponseEntity.ok(userService.getByUsername(userName));
    }

    @ApiOperation("Get user by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(userService.getById(id));
    }

}
