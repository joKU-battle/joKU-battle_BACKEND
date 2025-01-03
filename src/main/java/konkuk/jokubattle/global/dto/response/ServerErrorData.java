package konkuk.jokubattle.global.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ServerErrorData {

    private String errorClass;
    private String errorMessage;
}
