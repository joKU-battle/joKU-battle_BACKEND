package konkuk.jokubattle.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginReq {

    @Schema(description = "아이디", example = "konkukid")
    @NotBlank
    private String id;

    @Schema(description = "비밀번호", example = "password123")
    @NotBlank
    private String password;
}
