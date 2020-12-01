package course.spring.cooking.recipes.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    @NonNull
    Integer code;
    @NonNull
    String message;
    LocalDateTime timestamp = LocalDateTime.now();
    List<String> violations = new ArrayList<>();

    public ErrorResponse(@NonNull Integer code, @NonNull String message, List<String> violations) {
        this.code = code;
        this.message = message;
        this.violations = violations;
    }
}
