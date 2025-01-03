package konkuk.jokubattle.domain.joke.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import konkuk.jokubattle.domain.quiz.entity.Quiz;
import konkuk.jokubattle.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Joke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long joIdx;

    private String content;

    private int pickedCount;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "usIdx", nullable = false)
    private User user;

    public static Joke create(String content, User user) {
        return Joke.builder()
                .content(content)
                .pickedCount(0)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
