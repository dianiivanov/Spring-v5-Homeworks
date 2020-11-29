package course.spring.cooking.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import course.spring.cooking.recipes.enums.Gender;
import course.spring.cooking.recipes.enums.Role;
import course.spring.cooking.recipes.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @Pattern(regexp = "^[A-Fa-f0-9]{24}$")
    String id;
    @NonNull
    @NotNull
    private String name;
    @NonNull
    @NotNull
    @Size(max = 15)
    private String username;
    @NonNull
    @Size(min = 8)
    @Pattern(regexp = ".*\\d.*")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NonNull
    @NotNull
    private Gender gender;
    @NonNull
    @NotNull
    private Role role;
    @URL
    private String url;
    @Size(max = 512)
    private String introduction;
    @NotNull
    @NonNull
    Status status;

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toString()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return isActive();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return isActive();
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return isActive();
    }

    private boolean isActive(){
        return status == Status.ACTIVE;
    }
}