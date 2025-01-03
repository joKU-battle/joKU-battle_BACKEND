package konkuk.jokubattle.domain.quiz.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import konkuk.jokubattle.domain.quiz.dto.QuizSolveResponseDto;
import konkuk.jokubattle.domain.quiz.dto.request.QuizRequestDto;
import konkuk.jokubattle.domain.quiz.dto.request.QuizSolveRequestDto;
import konkuk.jokubattle.domain.quiz.dto.response.QuizRecommendResDto;
import konkuk.jokubattle.domain.quiz.dto.response.QuizResponseDto;
import konkuk.jokubattle.domain.quiz.service.QuizService;
import konkuk.jokubattle.global.annotation.CustomExceptionDescription;
import konkuk.jokubattle.global.annotation.UserIdx;
import konkuk.jokubattle.global.config.swagger.SwaggerResponseDescription;
import konkuk.jokubattle.global.dto.response.SuccessResponse;
import konkuk.jokubattle.global.exception.CustomException;
import konkuk.jokubattle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "퀴즈", description = "퀴즈 API")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("api/quizzes")
public class QuizController {

    private final QuizService quizService;

    @Operation(summary = "퀴즈 생성", description = "새로운 퀴즈를 생성합니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.QUIZ_CREATE)
    @PostMapping()
    public SuccessResponse<QuizResponseDto> createQuiz(
            @Validated @RequestBody QuizRequestDto requestDto,
            @Parameter(hidden = true) @UserIdx Long usIdx
    ) {
        return SuccessResponse.ok(quizService.createQuiz(usIdx, requestDto));
    }

    @Operation(summary = "퀴즈 목록 조회", description = "퀴즈 목록을 조회합니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.QUIZ_LIST)
    @GetMapping()
    public SuccessResponse<List<QuizResponseDto>> showTodayQuizzes() {
        return SuccessResponse.ok(quizService.getAllQuizzes());
    }

    @Operation(summary = "퀴즈 상세 조회", description = "퀴즈 ID로 상세 정보를 조회합니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.QUIZ_DETAIL)
    @GetMapping("{quizId}")
    public SuccessResponse<QuizResponseDto> getQuizDetails(
            @PathVariable("quizId") Long quizId
    ) {
        return quizService.getQuizById(quizId)
                .map(SuccessResponse::ok)
                .orElseThrow(() -> new CustomException(ErrorCode.QUIZ_NOT_FOUND));
    }

    @Operation(summary = "퀴즈 도전", description = "퀴즈의 정답을 제출합니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.QUIZ_ATTEMPT)
    @PostMapping("attempts/{quizId}")
    public SuccessResponse<QuizSolveResponseDto> solveQuiz(
            @PathVariable("quizId") Long quizId,
            @Validated @RequestBody QuizSolveRequestDto req
    ) {
        return SuccessResponse.ok(quizService.solveQuiz(quizId, req));
    }

    @Operation(summary = "퀴즈 추천", description = "퀴즈의 추천 수를 1 증가시킵니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.QUIZ_RECOMMEND)
    @PostMapping("recommendation")
    public SuccessResponse<QuizRecommendResDto> recommendQuiz(
            @Parameter(hidden = true) @UserIdx Long usIdx
    ) {
        return SuccessResponse.ok(quizService.increaseRecommendation(usIdx));
    }
}
