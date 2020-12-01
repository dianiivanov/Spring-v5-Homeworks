package course.spring.cooking.recipes.model;

import lombok.Data;

@Data
public class JwtResponse {
    private final User user;
    private final String token;
}
