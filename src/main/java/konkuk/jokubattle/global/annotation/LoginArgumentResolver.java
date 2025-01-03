package konkuk.jokubattle.global.annotation;

import konkuk.jokubattle.global.exception.CustomException;
import konkuk.jokubattle.global.exception.ErrorCode;
import konkuk.jokubattle.global.jwt.JwtTokenAuthentication;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserIdx.class) &&
                parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        validateAuthentication(authentication);

        return getUsIdxFromAuthentication(authentication);
    }

    private void validateAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }

    private Long getUsIdxFromAuthentication(Authentication authentication) {
        if (!(authentication instanceof JwtTokenAuthentication)) {
            throw new CustomException(ErrorCode.AUTHENTICATION_SETTING_FAIL);
        }
        return ((JwtTokenAuthentication) authentication).getUsIdx();
    }
}
