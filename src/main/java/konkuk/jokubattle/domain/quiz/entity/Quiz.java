package konkuk.jokubattle.domain.quiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import konkuk.jokubattle.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private int recommendation;

    @Column(nullable = false)
    private int correct;

    @Column(nullable = false)
    private int wrong;

    @Column(nullable = false)
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

    public void recommend() {
        this.recommendation++;
        user.increaseScore();
    }
}
