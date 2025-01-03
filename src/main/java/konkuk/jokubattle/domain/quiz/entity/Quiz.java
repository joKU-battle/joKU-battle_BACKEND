package konkuk.jokubattle.domain.quiz.entity;

import jakarta.persistence.*;
import konkuk.jokubattle.domain.user.entity.User;
import java.time.LocalDateTime;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quIdx;

    private String question;

    private String answer;

    private int recommendation;

    private int correct;

    private int wrong;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "usIdx", nullable = false)
    private User user;

    public static Quiz create(String question, String answer, User user) {
        return Quiz.builder()
                .question(question)
                .answer(answer)
                .recommendation(0)
                .correct(0)
                .wrong(0)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
