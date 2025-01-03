package konkuk.jokubattle.domain.joke.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import konkuk.jokubattle.domain.joke.dto.request.JokeRequestDto;
import konkuk.jokubattle.domain.joke.dto.response.JokeResponseDto;
import konkuk.jokubattle.domain.joke.dto.response.JokeWorldCupRes;
import konkuk.jokubattle.domain.joke.service.JokeService;
import konkuk.jokubattle.global.annotation.CustomExceptionDescription;
import konkuk.jokubattle.global.annotation.UserIdx;
import konkuk.jokubattle.global.config.swagger.SwaggerResponseDescription;
import konkuk.jokubattle.global.dto.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "우스운 말", description = "우스운 말 API")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("api/jokes")
public class JokeController {
    private final JokeService jokeService;

    @Operation(summary = "우스운말 목록", description = "우스운말 목록을 조회합니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.JOKE_LIST)
    @GetMapping()
    public SuccessResponse<List<JokeResponseDto>> listJokes(
                                                            @RequestParam int month,
                                                            @RequestParam int week
    ) {
        return SuccessResponse.ok(jokeService.getAllJokes(month, week));
    }

    @Operation(summary = "우스운말 생성", description = "새로운 우스운말을 생성합니다.")
    @CustomExceptionDescription(SwaggerResponseDescription.JOKE_CREATE)
    @PostMapping()
    public SuccessResponse<JokeResponseDto> createJoke(
            @Validated @RequestBody JokeRequestDto requestDto,
            @Parameter(hidden = true) @UserIdx Long usIdx
    ) {
        return SuccessResponse.ok(jokeService.createJoke(usIdx, requestDto));
    }

    @Operation(summary = "우스운말 월드컵 시작", description = "우스운말 월드컵에서 진행될 최대 8개의 데이터를 조회한다.")
    @CustomExceptionDescription(SwaggerResponseDescription.JOKE_WORLDCUP)
    @GetMapping("worldcup")
    public SuccessResponse<List<JokeWorldCupRes>> jokeWorldCup() {
        return SuccessResponse.ok(jokeService.jokeWorldCup());
    }

    @Operation(summary = "우스운말 월드컵 선택", description = "우스운말 월드컵에서 하나를 선택한다.")
    @CustomExceptionDescription(SwaggerResponseDescription.JOKE_WORLDCUP_SELECT)
    @PutMapping("worldcup/{joIdx}")
    public SuccessResponse<Boolean> worldCupSelect(
            @PathVariable("joIdx") Long joIdx
    ) {
        return SuccessResponse.ok(jokeService.worldCupSelect(joIdx));
    }
}
