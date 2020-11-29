package course.spring.cooking.recipes.dao;

import course.spring.cooking.recipes.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
