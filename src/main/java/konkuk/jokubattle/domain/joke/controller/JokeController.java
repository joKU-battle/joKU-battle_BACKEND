package konkuk.jokubattle.domain.joke.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import konkuk.jokubattle.domain.joke.dto.request.JokeRequestDto;
import konkuk.jokubattle.domain.joke.dto.response.JokeResponseDto;
import konkuk.jokubattle.domain.joke.service.JokeService;
import konkuk.jokubattle.global.annotation.CustomExceptionDescription;
import konkuk.jokubattle.global.config.swagger.SwaggerResponseDescription;
import konkuk.jokubattle.global.dto.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "우스운 말", description = "우스운 말 API")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("api/jokes")
public class JokeController {
    private final JokeService jokeService;

    @Operation(summary = "우스운 말 목록", description = "우스운 말 목록을 조회합니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.JOKE_LIST)
    @GetMapping()
    public SuccessResponse<List<JokeResponseDto>> listJokes(
                                                            @RequestParam int month,
                                                            @RequestParam int week
    ) {
        return SuccessResponse.ok(jokeService.getAllJokes(month, week));
    }

    @Operation(summary = "우스운 말 생성", description = "새로운 우스운 말을 생성합니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.JOKE_CREATE)
    @PostMapping()
    public SuccessResponse<JokeResponseDto> createJoke(@Validated @RequestBody JokeRequestDto requestDto) {
        return SuccessResponse.ok(jokeService.createJoke(requestDto));
    }

//    @Operation(summary = "월드컵 8강 진행", description = "우스운 말 투표를 시작합니다.")
//    @


}
