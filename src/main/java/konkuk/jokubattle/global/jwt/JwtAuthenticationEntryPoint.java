package konkuk.jokubattle.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import konkuk.jokubattle.global.dto.response.ErrorResponse;
import konkuk.jokubattle.global.exception.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");

        ErrorCode error = ErrorCode.UNAUTHORIZED;
        response.setStatus(error.getHttpCode());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = new ObjectMapper().writeValueAsString(ErrorResponse.of(error.getErrorCode(), error.getMessage()));
        response.getWriter().write(json);
    }
}