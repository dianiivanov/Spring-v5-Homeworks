package course.spring.cooking.recipes.service;

import course.spring.cooking.recipes.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(String id);
    User updateUser(User user);
    User deleteUserById(String id);
}
