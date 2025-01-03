package konkuk.jokubattle.domain.quiz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuizRecommendResDto {
    private Long quizId;
    private int recommendation;
}
