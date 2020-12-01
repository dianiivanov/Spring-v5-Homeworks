package course.spring.cooking.recipes.web;

import course.spring.cooking.recipes.exception.InvalidDataException;
import course.spring.cooking.recipes.model.Recipe;
import course.spring.cooking.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static course.spring.cooking.recipes.util.ErrorUtils.getAllErrors;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @GetMapping
    List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @PostMapping
    ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidDataException("Invalid recipe data", getAllErrors(errors));
        }
        final Recipe createdRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromUriString("/api/users/{userId}/recipes/{recipeId}")
                .buildAndExpand(createdRecipe.getUserId(), createdRecipe.getId()).toUri()).body(createdRecipe);
    }

    @GetMapping("/{id}")
    Recipe getRecipe(@PathVariable String id) {
        return recipeService.getRecipeByIdForCurrentUser(id);
    }

    @PutMapping("/{id}")
    Recipe updateRecipe(@PathVariable String id, @Valid @RequestBody Recipe recipe, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidDataException("Invalid recipe data.", getAllErrors(errors));
        }
        if (!id.equals(recipe.getId())) {
            throw new InvalidDataException(String.format(
                    "Invalid recipe data. Id = %s of the url differs from id = %s of the recipe body.", id, recipe.getId()));
        }
        return recipeService.updateRecipe(recipe);
    }

    @DeleteMapping("/{id}")
    Recipe deleteRecipe(@PathVariable String id) {
        return recipeService.deleteRecipeById(id);
    }
}
