package course.spring.cooking.recipes.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    @NonNull
    String message;

    List<String> violations = new ArrayList<>();
}
