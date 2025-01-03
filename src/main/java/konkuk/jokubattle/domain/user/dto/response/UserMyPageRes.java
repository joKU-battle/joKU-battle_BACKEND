package konkuk.jokubattle.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import konkuk.jokubattle.domain.title.dto.response.TitleDetailRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserMyPageRes {

    @Schema(description = "프로필 이미지 url")
    String imageUrl;

    @Schema(description = "사용자 이름")
    String name;

    @Schema(description = "학과")
    String department;

    TitleDetailRes title;
}
