package konkuk.jokubattle.domain.joke.dto.request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class JokeRequestDto {
    private String content;
    private String userName;
    private String userId;
}
