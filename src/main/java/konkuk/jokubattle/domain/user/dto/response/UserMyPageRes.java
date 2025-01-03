package konkuk.jokubattle.domain.user.dto.response;

import java.util.List;
import konkuk.jokubattle.domain.title.dto.response.TitleRes;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserMyPageRes {

    List<TitleRes> titles;
}
