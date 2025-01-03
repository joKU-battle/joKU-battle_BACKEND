package konkuk.jokubattle.domain.quiz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuizResponseDto {
    private Long quizId;
    private String question;
    private String createdAt;

    private String userName;
    private String userDepartment;

    private int recommendation;
    private int correct;
    private int wrong;
}
