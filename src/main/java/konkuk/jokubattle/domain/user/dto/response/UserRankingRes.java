package konkuk.jokubattle.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRankingRes {

    @Schema(description = "이름")
    private String name;

    @Schema(description = "학과")
    private String department;

    @Schema(description = "점수")
    private int score;

    @Schema(description = "프로필 이미지")
    private String image;
}
