package konkuk.jokubattle.domain.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuizResponseDto {
    private Long id;
    private String question;
    private String createdAt;

    private String userName;
    private String userDepartment;

    private int recommendation;
}
