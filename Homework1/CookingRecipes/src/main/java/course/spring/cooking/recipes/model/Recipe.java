package course.spring.cooking.recipes.model;

import lombok.Data;
import lombok.NonNull;
import org.apache.tomcat.jni.Local;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Document(collection = "recipes")
@Data
public class Recipe {
    //    идентификатор на рецептата (MongoDB ObjectID - 24 символа);
//    идентификатор на потребителя споделил рецептата (MongoDB ObjectID - 24 символа);
//    име на рецептата (до 80 символа);
//    кратко описание на рецептата (до 256 символа);
//    време за приготвяне (в минути);
//    използвани продукти (списък от продукти - JSON Array);
//    снимка на резултата от рецептата (може да бъде Data URL във формат data: [<mediatype></mediatype>][;base64],<data></data> или друг валиден URL, задължителен атрибут);
//    подробно описание (до 2048 символа);
//    ключови думи - tags (списък от тагове - JSON Array);
//    дата и час на споделяне (генерира се автоматично);
//    дата и час на последна модификация (генерира се автоматично);
    @Id
    @Pattern(regexp = "^[A-Fa-f0-9]{24}$")
    String id;
    @Pattern(regexp = "^[A-Fa-f0-9]{24}$")
    String userId;
    @NotNull
    @NonNull
    @Size(max = 80)
    private String name;
    @NotNull
    @NonNull
    @Size(max = 256)
    private String shortDescription;
    @NotNull
    @NonNull
    private String longDescription;
    @NotNull
    @NonNull
    @Positive
    private Integer minutes;
    @URL
    private String image;
    @NonNull
    private List<String> products = new ArrayList<>();
    private List<String> keywords = new ArrayList<>();

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}
