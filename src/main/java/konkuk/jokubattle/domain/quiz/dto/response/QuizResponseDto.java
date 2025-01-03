package konkuk.jokubattle.domain.quiz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
}
