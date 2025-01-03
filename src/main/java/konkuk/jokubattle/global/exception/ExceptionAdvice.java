package konkuk.jokubattle.global.exception;

import java.util.ArrayList;
import java.util.List;
import konkuk.jokubattle.global.dto.response.ParameterData;
import konkuk.jokubattle.global.dto.response.ServerErrorData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 등록되지 않은 예외
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ServerErrorData> handleUntrackedException(Exception e) {
        log.error("[UNTRACKED ERROR] class: [{}], message: [{}]",
            e.getClass().getSimpleName(),
            e.getMessage());

        ServerErrorData serverErrorData = ServerErrorData.builder()
            .errorClass(e.getClass().toString())
            .errorMessage(e.getMessage())
            .build();
        return new ResponseEntity<>(serverErrorData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 파라미터 검증 예외
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<List<ParameterData>> handleValidationExceptions(MethodArgumentNotValidException e) {
        log.warn("[PARAMETER VALIDATION EXCEPTION] class: [{}], message: [{}], localizedMessage: [{}]",
            e.getClass().getSimpleName(),
            e.getMessage(),
            e.getLocalizedMessage());

        List<ParameterData> list = new ArrayList<>();

        BindingResult bindingResult = e.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            ParameterData parameterData = ParameterData.builder()
                .key(fieldError.getField())
                .value(fieldError.getRejectedValue() == null ? null : fieldError.getRejectedValue().toString())
                .reason(fieldError.getDefaultMessage())
                .build();
            list.add(parameterData);
        }

        return new ResponseEntity<>(list, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * 파라미터 문법 예외
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<String> handleHttpMessageParsingExceptions(HttpMessageNotReadableException e) {
        log.warn("[PARAMETER GRAMMAR EXCEPTION] class: [{}], message: [{}]",
            e.getClass().getSimpleName(),
            e.getMessage());

        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * 매개변수 타입 예외
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<String> handleHttpMessageParsingExceptions(MethodArgumentTypeMismatchException e) {
        log.warn("[METHOD ARGUMENT TYPE EXCEPTION] class: [{}], message: [{}]",
            e.getClass().getSimpleName(),
            e.getMessage());

        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * 경로 존재하지 않음
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleInvalidPathExceptions(NoHandlerFoundException e) {
        String meessage = e.getMessage();
        return new ResponseEntity<>(meessage, HttpStatus.NOT_FOUND);
    }

}