package course.spring.cooking.recipes.service.impl;

import course.spring.cooking.recipes.dao.RecipeRepository;
import course.spring.cooking.recipes.enums.Role;
import course.spring.cooking.recipes.exception.EntityNotFoundException;
import course.spring.cooking.recipes.model.Recipe;
import course.spring.cooking.recipes.model.User;
import course.spring.cooking.recipes.service.RecipeService;
import course.spring.cooking.recipes.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class DefaultRecipeService implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserService userService;

    @Override
    public Recipe createRecipe(Recipe recipe) {
        recipe.setId(null);
        final User user = userService.getCurrentUser();
        recipe.setUserId(user.getId());
        return recipeRepository.insert(recipe);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeByIdForCurrentUser(String id) {
        final Recipe recipe = getRecipeById(id);
        final User user = userService.getCurrentUser();
        if (user.getRole()!= Role.ADMIN && !user.getId().equals(recipe.getUserId())) {
            throw new EntityNotFoundException(String.format("Could not find recipe with id = %s for the current user", recipe.getId()));
        }
        return recipe;
    }

    @Override
    public Recipe getRecipeById(String id) {
        final Recipe recipe = recipeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Could not find recipe with id = %s", id)));
        return recipe;
    }

    @Override
    public Recipe updateRecipe(Recipe recipe) {
        getRecipeByIdForCurrentUser(recipe.getId());
        recipe.setModified(LocalDateTime.now());
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe deleteRecipeById(String id) {
        Recipe recipeToDelete = getRecipeByIdForCurrentUser(id);
        recipeRepository.deleteById(id);
        final User user = userService.getCurrentUser();
        if (!user.getId().equals(recipeToDelete.getUserId())) {
            throw new EntityNotFoundException(String.format("Could not find recipe with id = %s for the current user", recipeToDelete.getId()));
        }
        return recipeToDelete;
    }
}
