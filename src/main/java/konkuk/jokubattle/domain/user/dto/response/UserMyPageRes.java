package konkuk.jokubattle.domain.user.dto.response;

import konkuk.jokubattle.domain.title.dto.response.TitleDetailRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserMyPageRes {

    String imageUrl;

    String name;

    String department;

    TitleDetailRes title;
}
