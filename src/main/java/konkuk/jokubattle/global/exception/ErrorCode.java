package konkuk.jokubattle.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    /**
     * 에러코드 규약
     * HTTP Status Code는 에러에 가장 유사한 코드를 부여한다.(Swagger에서 구분하여 Response Example로 출력된다.)
     * 사용자정의 에러코드는 음수를 사용한다.
     * 사용자정의 에러코드는 중복되지 않게 배정한다.
     * 사용자정의 에러코드는 각 카테고리 별로 100단위씩 끊어서 배정한다. 단, Common 카테고리는 -100 단위를 고정으로 가져간다.
     */

    /**
     * 401 : 미승인 403 : 권한의 문제가 있을때 406 : 객체가 조회되지 않을 때 409 : 현재 데이터와 값이 충돌날 때(ex. 아이디 중복) 412 : 파라미터 값이 뭔가 누락됐거나 잘못 왔을 때
     * 422 : 파라미터 문법 오류 424 : 뭔가 단계가 꼬였을때, 1번안하고 2번하고 그런경우
     */

    //Common
    SERVER_UNTRACKED_ERROR(-100, "미등록 서버 에러입니다. 서버 팀에 연락주세요.", 500),
    OBJECT_NOT_FOUND(-101, "조회된 객체가 없습니다.", 406),
    INVALID_PARAMETER(-102, "잘못된 파라미터입니다.", 422),
    PARAMETER_VALIDATION_ERROR(-103, "파라미터 검증 에러입니다.", 422),
    PARAMETER_GRAMMAR_ERROR(-104, "파라미터 문법 에러입니다.", 422),
    INVALID_ORDER_PARAMETER(-105, "잘못된 정렬 조건 파라미터입니다.", 422),
    INVALID_TYPE_PARAMETER(-106, "잘못된 타입 파라미터입니다.", 422),
    OBJECT_DELETE_ERROR(-107, "객체 삭제 에러입니다.", 422),
    NOT_FOUND_PATH(-108, "존재하지 않는 API 경로입니다.", 404),
    ERROR_FIREBASE_KEY_NOT_FOUND(-109, "파이어베이스 서비스 키에 대한 json 을 찾지 못 했습니다.", 422),
    ERROR_GOOGLE_CREDENTIAL(-110, "구글 크레덴셜 생성 에러가 발생하였습니다.", 422),
    ERROR_MTONET(-111, "안심번호 서비스 에러가 발생했습니다.", 422),

    //Auth
    UNAUTHORIZED(-200, "인증 자격이 없습니다.", 401),
    FORBIDDEN(-201, "권한이 없습니다.", 403),
    JWT_ERROR_TOKEN(-202, "잘못된 토큰입니다.", 401),
    JWT_EXPIRE_TOKEN(-203, "만료된 토큰입니다.", 401),
    AUTHORIZED_ERROR(-204, "인증 과정 중 에러가 발생했습니다.", 500),
    JWT_EXPIRE_REFRESH_TOKEN(-205, "관리하고 있지 않은 리프레시 토큰입니다", 401),
    JWT_UNMATCHED_CLAIMS(-206, "토큰 인증 정보가 일치하지 않습니다", 401),
    AUTHENTICATION_SETTING_FAIL(-207, "인증정보 처리에 실패했습니다.", 500),

    // User
    USER_NOT_FOUND(-300, "존재하지 않는 회원입니다.", 406),
    USER_DUPLICATE_ID(-301, "이미 존재하는 아이디입니다.", 401),
    USER_NOT_MATCH_PASSWORD(-302, "비밀번호가 일치하지 않습니다.", 403),


    //Quiz
    QUIZ_NOT_FOUND(-400, "퀴즈를 찾을 수 없습니다.", 406),
    QUIZ_ALREADY_EXISTS(-401, "이미 존재하는 퀴즈입니다.", 401),

    //Joke
    JOKE_ALREADY_EXISTS(-500,"이미 존재하는 조크입니다. ", 401),
    JOKE_NOT_FOUND(-501,"해당 주차에 등록된 조크가 없습니다.",406)
    ;


    private final int errorCode;
    private final String message;
    private final int httpCode;
}
