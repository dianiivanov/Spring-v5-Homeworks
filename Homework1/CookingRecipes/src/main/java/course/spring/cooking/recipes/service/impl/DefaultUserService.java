package course.spring.cooking.recipes.service.impl;

import course.spring.cooking.recipes.dao.UserRepository;
import course.spring.cooking.recipes.enums.Gender;
import course.spring.cooking.recipes.exception.EntityNotFoundException;
import course.spring.cooking.recipes.model.User;
import course.spring.cooking.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class DefaultUserService implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        if (Objects.isNull(user.getUrl())) {
            final String url = (user.getGender() == Gender.MALE) ?
                    "https://www.flaticon.com/svg/static/icons/svg/0/93.svg" :
                    "https://www.clipartmax.com/png/middle/5-55403_blank-avatar-profile-pic-icon-female.png";
            user.setUrl(url);
        }
        return userRepository.insert(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Could not find user with id = %s", id)));
    }

    @Override
    public User updateUser(User user) {
        User oldUser = getUserById(user.getId());
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(oldUser.getPassword()));
        return userRepository.save(user);
    }

    public User deleteUserById(String id) {
        User removedUser = getUserById(id);
        userRepository.deleteById(id);
        return removedUser;
    }
}



