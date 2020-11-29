package course.spring.cooking.recipes.model;

import lombok.NonNull;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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
    @NotNull
    @NonNull
    @Size(max=80)
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

    private List<String> products = new ArrayList<>();
    private List<String> keywords = new ArrayList<>();
}
