package course.spring.cooking.recipes.config;

import course.spring.cooking.recipes.enums.Role;
import course.spring.cooking.recipes.service.UserService;
import course.spring.cooking.recipes.web.JwtAuthenticationEntryPoint;
import course.spring.cooking.recipes.web.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import course.spring.cooking.recipes.web.FilterChainExceptionHandlerFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtAuthenticationEntryPoint jwt;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    FilterChainExceptionHandlerFilter filterChainExceptionHandlerFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(POST, "/api/login").permitAll()
                .antMatchers(POST, "/api/register").permitAll()
                .antMatchers("/api/users/**").hasAnyRole(Role.ADMIN.toString())
                .antMatchers(GET, "/api/recipes/**").hasAnyRole(Role.ADMIN.toString(), Role.USER.toString())
                .antMatchers(PUT, "/api/recipes/**").hasAnyRole(Role.ADMIN.toString(), Role.USER.toString())
                .antMatchers(DELETE, "/api/recipes/**").hasAnyRole(Role.ADMIN.toString(), Role.USER.toString())
                .antMatchers(POST,"/api/recipes").hasAnyRole(Role.USER.toString())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwt)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(filterChainExceptionHandlerFilter, LogoutFilter.class);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return userService::getUserByUsername;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
