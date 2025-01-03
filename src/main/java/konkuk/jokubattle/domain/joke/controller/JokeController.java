package konkuk.jokubattle.domain.joke.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import konkuk.jokubattle.domain.joke.dto.request.JokeRequestDto;
import konkuk.jokubattle.domain.joke.dto.response.JokeResponseDto;
import konkuk.jokubattle.domain.joke.dto.response.JokeWorldCupRes;
import konkuk.jokubattle.domain.joke.service.JokeService;
import konkuk.jokubattle.global.annotation.CustomExceptionDescription;
import konkuk.jokubattle.global.config.swagger.SwaggerResponseDescription;
import konkuk.jokubattle.global.dto.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "우스운 말", description = "우스운 말 API")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("api/joke")
public class JokeController {
    private final JokeService jokeService;

    @Operation(summary = "우스운 말 목록", description = "우스운 말 목록을 조회합니다.")
    @GetMapping("list")
    public ResponseEntity<List<JokeResponseDto>> listJokes() {
        List<JokeResponseDto> jokeResponseDtos = jokeService.getAllJokes();
        return ResponseEntity.status(HttpStatus.OK).body(jokeResponseDtos);
    }

    @Operation(summary = "우스운 말 생성", description = "새로운 우스운 말을 생성합니다.")
    @PostMapping("create")
    public ResponseEntity<JokeResponseDto> createJoke(@Validated @RequestBody JokeRequestDto requestDto) {
        JokeResponseDto responseDto = jokeService.createJoke(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "우스운말 월드컵 시작", description = "우스운말 월드컵에서 진행될 최대 8개의 데이터를 조회한다.")
    @CustomExceptionDescription(SwaggerResponseDescription.JOKE_WORLDCUP)
    @GetMapping("worldcup")
    public SuccessResponse<List<JokeWorldCupRes>> jokeWorldCup() {
        return SuccessResponse.ok(jokeService.jokeWorldCup());
    }


}
