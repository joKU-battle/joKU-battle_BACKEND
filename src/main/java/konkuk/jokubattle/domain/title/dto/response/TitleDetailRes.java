package konkuk.jokubattle.domain.title.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TitleDetailRes {

    private String name;

    private int month;

    private int week;
}
