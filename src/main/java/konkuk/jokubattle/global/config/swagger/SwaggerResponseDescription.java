package konkuk.jokubattle.global.config.swagger;

import java.util.LinkedHashSet;
import java.util.Set;
import konkuk.jokubattle.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum SwaggerResponseDescription {

    USER_SIGNUP(new LinkedHashSet<>(Set.of(
            ErrorCode.USER_DUPLICATE_ID
    ))),
    USER_LOGIN(new LinkedHashSet<>(Set.of(
            ErrorCode.USER_NOT_MATCH_PASSWORD
    ))),
    USER_MYPAGE(new LinkedHashSet<>(Set.of())),
    USER_RANKING(new LinkedHashSet<>(Set.of())),

    //Quiz
    QUIZ_CREATE(new LinkedHashSet<>(Set.of(
            ErrorCode.QUIZ_ALREADY_EXISTS
    ))),
    QUIZ_LIST(new LinkedHashSet<>(Set.of())),
    QUIZ_DETAIL(new LinkedHashSet<>(Set.of(
            ErrorCode.QUIZ_NOT_FOUND
    ))),
    QUIZ_ATTEMPT(new LinkedHashSet<>(Set.of(
            ErrorCode.QUIZ_NOT_FOUND
    ))),
    QUIZ_RECOMMEND(new LinkedHashSet<>(Set.of(
    ))),

    //Joke
    JOKE_CREATE(new LinkedHashSet<>(Set.of(
            ErrorCode.JOKE_ALREADY_EXISTS
    ))),
    JOKE_LIST(new LinkedHashSet<>(Set.of()))
    ,

    JOKE_WORLDCUP(new LinkedHashSet<>(Set.of())),
    JOKE_WORLDCUP_SELECT(new LinkedHashSet<>(Set.of())),

    ;


    private Set<ErrorCode> errorCodeList;

    SwaggerResponseDescription(Set<ErrorCode> errorCodeList) {
        // 공통 에러
        errorCodeList.addAll(new LinkedHashSet<>(Set.of(
                ErrorCode.SERVER_UNTRACKED_ERROR,
                ErrorCode.INVALID_PARAMETER,
                ErrorCode.PARAMETER_VALIDATION_ERROR,
                ErrorCode.PARAMETER_GRAMMAR_ERROR,
                ErrorCode.UNAUTHORIZED,
                ErrorCode.FORBIDDEN,
                ErrorCode.JWT_ERROR_TOKEN,
                ErrorCode.JWT_EXPIRE_TOKEN,
                ErrorCode.AUTHORIZED_ERROR,
                ErrorCode.OBJECT_NOT_FOUND
        )));

        if (this.name().contains("USER_")) {
            errorCodeList.add(ErrorCode.USER_NOT_FOUND);
        }

        this.errorCodeList = errorCodeList;
    }
}
