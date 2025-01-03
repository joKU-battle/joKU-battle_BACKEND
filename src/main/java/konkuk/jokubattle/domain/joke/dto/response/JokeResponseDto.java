package konkuk.jokubattle.domain.joke.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JokeResponseDto {
    private Long joIdx;
    private String content;
    private String createdAt;

    private String userName;
    private String userDepartment;

    private int pickedCount;
}
