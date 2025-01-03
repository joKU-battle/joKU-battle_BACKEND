package konkuk.jokubattle.domain.quiz.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import konkuk.jokubattle.domain.quiz.dto.QuizRequestDto;
import konkuk.jokubattle.domain.quiz.dto.QuizResponseDto;
import konkuk.jokubattle.domain.quiz.dto.request.QuizRecommendReqDto;
import konkuk.jokubattle.domain.quiz.dto.request.QuizSolveRequestDto;
import konkuk.jokubattle.domain.quiz.dto.QuizSolveResponseDto;
import konkuk.jokubattle.domain.quiz.dto.response.QuizRecommendResDto;
import konkuk.jokubattle.domain.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "퀴즈", description = "퀴즈 API")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("api/quiz")
public class QuizController {

    private final QuizService quizService;

    @Operation(summary = "퀴즈 생성", description = "새로운 퀴즈를 생성합니다.")
    @PostMapping("create")
    public ResponseEntity<QuizResponseDto> createQuiz(
            @Validated @RequestBody QuizRequestDto requestDto
    ) {
        log.info("createQuiz 요청: {}", requestDto);
        QuizResponseDto responseDto = quizService.createQuiz(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "퀴즈 목록 조회", description = "퀴즈 목록을 조회합니다.")
    @GetMapping("list")
    public ResponseEntity<List<QuizResponseDto>> showQuizzes() {
        log.info("showQuizzes 요청");
        List<QuizResponseDto> responseDtos = quizService.getAllQuizzes();
        return ResponseEntity.ok(responseDtos);
    }

    @Operation(summary = "퀴즈 상세 조회", description = "퀴즈 ID로 상세 정보를 조회합니다.")
    @GetMapping("detail")
    public ResponseEntity<QuizResponseDto> getQuizDetails(
            @RequestParam Long quizId
    ) {
        log.info("getQuizDetails 요청: quizId={}", quizId);
        return quizService.getQuizById(quizId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "퀴즈 도전", description = "퀴즈의 정답을 제출합니다.")
    @PostMapping("solve")
    public ResponseEntity<QuizSolveResponseDto> solveQuiz(
            @Validated @RequestBody QuizSolveRequestDto requestDto
    ) {
        log.info("solveQuiz 요청: quizId={}, answer={}", requestDto.getQuizId(), requestDto.getAnswer());
        QuizSolveResponseDto responseDto = quizService.solveQuiz(requestDto);
        if ("정답입니다!".equals(responseDto.getResultMessage())) {
            return ResponseEntity.ok(responseDto);
        } else if ("틀렸습니다!".equals(responseDto.getResultMessage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    @Operation(summary = "퀴즈 추천", description = "퀴즈의 추천 수를 1 증가시킵니다.")
    @PostMapping("recommendation")
    public ResponseEntity<QuizRecommendResDto> recommendQuiz(
            @Validated @RequestBody QuizRecommendReqDto requestDto
    ){
        log.info("recommendQuiz 요청 : quizId={}",requestDto.getQuizId());
        QuizRecommendResDto quizRecommendResDto = quizService.increaseRecommendation(requestDto);
        return ResponseEntity.ok(quizRecommendResDto);
    }
}
