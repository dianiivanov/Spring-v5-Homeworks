package course.spring.cooking.recipes.web;

import course.spring.cooking.recipes.enums.Role;
import course.spring.cooking.recipes.exception.InvalidDataException;
import course.spring.cooking.recipes.exception.InvalidRegistrationDataException;
import course.spring.cooking.recipes.model.Credentials;
import course.spring.cooking.recipes.model.JwtResponse;
import course.spring.cooking.recipes.model.User;
import course.spring.cooking.recipes.service.UserService;
import course.spring.cooking.recipes.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Objects;

import static course.spring.cooking.recipes.util.ErrorUtils.getAllErrors;

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody Credentials credentials, Errors errors){
        if(errors.hasErrors()){
            throw new InvalidDataException("Invalid data.", getAllErrors(errors));
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
        final User user = userService.getUserByUsername(credentials.getUsername());
        final String token = jwtUtils.generateToken(user);
        log.info("{} logged in successfully, token: {}",user.getUsername(),token);
        return new JwtResponse(user, token);
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody User user, Errors errors){
        if(errors.hasErrors()){
            throw new InvalidDataException("Invalid data.", getAllErrors(errors));
        }
        try {
            final User duplicate = userService.getUserByUsername(user.getUsername());
            if (Objects.nonNull(duplicate)) {
                throw new InvalidRegistrationDataException(String.format("Username '%s' already exists.", user.getUsername()));
            }
        }catch(InvalidDataException exception) {
            user.setRole(Role.USER);
        }
        return userService.createUser(user);
    }
}
