package konkuk.jokubattle.domain.quiz.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuizSolveResponseDto {
    private Long quizId;
    private String resultMessage;

    public QuizSolveResponseDto(Long quizId, String resultMessage) {
        this.quizId = quizId;
        this.resultMessage = resultMessage;
    }
}
