package konkuk.jokubattle.domain.quiz.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuizRequestDto {
    private String question;
    private String answer;
    private Long userId;
}
