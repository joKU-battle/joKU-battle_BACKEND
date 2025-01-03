package konkuk.jokubattle.domain.title.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TitleDetailRes {

    @Schema(description = "칭호명", example = "아재개그 대마왕")
    private String name;

    @Schema(description = "획득 월", example = "1")
    private int month;

    @Schema(description = "획득 주차", example = "1")
    private int week;
}
