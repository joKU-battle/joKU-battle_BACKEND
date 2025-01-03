package konkuk.jokubattle.domain.quiz.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuizRequestDto {
    private String question;
    private String answer;
}
