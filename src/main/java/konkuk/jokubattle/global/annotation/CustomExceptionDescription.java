package konkuk.jokubattle.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import konkuk.jokubattle.global.config.swagger.SwaggerResponseDescription;

/**
 * Swagger에 Exception Response Description을 설정하기 위한 어노테이션
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomExceptionDescription {

    SwaggerResponseDescription value();
}
