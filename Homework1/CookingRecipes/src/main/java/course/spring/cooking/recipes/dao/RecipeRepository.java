package course.spring.cooking.recipes.dao;

import course.spring.cooking.recipes.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe,String> {
}
