package course.spring.cooking.recipes.service;

import course.spring.cooking.recipes.model.Recipe;
import course.spring.cooking.recipes.model.User;

import java.util.List;

public interface RecipeService {
    Recipe createRecipe(Recipe recipe);
    List<Recipe> getAllRecipes();
    Recipe getRecipeByIdForCurrentUser(String id);
    Recipe getRecipeById(String id);
    Recipe updateRecipe(Recipe recipe);
    Recipe deleteRecipeById(String id);
}
