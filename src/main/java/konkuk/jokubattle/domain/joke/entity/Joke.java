package konkuk.jokubattle.domain.joke.entity;

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

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Joke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long joIdx;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int pickedCount;

    @Column(nullable = false)
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

    public void select() {
        pickedCount++;
        user.increaseScore();
    }
}
