package konkuk.jokubattle.domain.joke.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JokeWorldCupRes {

    @Schema(description = "우스운말 인덱스", example = "1")
    private Long joIdx;

    @Schema(description = "우스운말 내용")
    private String content;
}
