package course.spring.cooking.recipes.web;

import course.spring.cooking.recipes.exception.InvalidDataException;
import course.spring.cooking.recipes.model.User;
import course.spring.cooking.recipes.service.UserService;

import static course.spring.cooking.recipes.util.ErrorUtils.getAllErrors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    ResponseEntity<User> postUser(@Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidDataException("Invalid user data", getAllErrors(errors));
        }
        final User createdUser = userService.createUser(user);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}").buildAndExpand(createdUser.getId()).toUri()).body(createdUser);
    }

    @GetMapping("/{id:^[A-Fa-f0-9]{24}$}")
    User getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id:^[A-Fa-f0-9]{24}$}")
    User updateUser(@PathVariable("id") String id, @Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidDataException("Invalid user data", getAllErrors(errors));
        }
        if (!id.equals(user.getId())) {
            throw new InvalidDataException(
                    String.format("User ID=%s in the URL differs from ID=%s in the User body", id, user.getId()));
        }

        return userService.updateUser(user);
    }

    @DeleteMapping("/{id:^[A-Fa-f0-9]{24}$}")
    User deleteUser(@PathVariable("id") String id){
        return userService.deleteUserById(id);
    }
}
