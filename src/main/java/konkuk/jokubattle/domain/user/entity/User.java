package konkuk.jokubattle.domain.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import konkuk.jokubattle.domain.title.entity.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = User.UserBuilder.class)
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usIdx;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String department;

    @Builder.Default
    private int score = 0;

    private String image;

    @OneToMany
    @JoinColumn(name = "us_idx")
    private List<Title> titles;

    @Column(nullable = false, length = 500)
    private LocalDateTime createdAt;


    public static User create(String id, String password, String name, String department, String image) {
        return User.builder()
                .id(id)
                .password(password)
                .name(name)
                .department(department)
                .image(image)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void increaseScore() {
        score++;
    }
}
