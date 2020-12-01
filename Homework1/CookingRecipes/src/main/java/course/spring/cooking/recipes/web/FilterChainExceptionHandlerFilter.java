package course.spring.cooking.recipes.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import course.spring.cooking.recipes.model.ErrorResponse;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class FilterChainExceptionHandlerFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ErrorHandlingControllerAdvice controllerAdvice;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        }catch(JwtException | AuthenticationException e){
            log.error("Spring Security Filter Chain Exception:", e);
            log.info(request.getPathInfo());
            ResponseEntity<ErrorResponse> responseEntity = controllerAdvice.handleAuthenticaionException(e);
            response.setStatus(responseEntity.getStatusCodeValue());
            PrintWriter out = response.getWriter();
            new ObjectMapper().writeValue(out, responseEntity.getBody());
        }
    }
}
