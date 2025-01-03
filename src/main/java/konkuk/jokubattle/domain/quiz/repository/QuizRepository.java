package konkuk.jokubattle.domain.quiz.repository;

import konkuk.jokubattle.domain.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Quiz save(Quiz quiz);
    boolean existsByQuestion(String question);
    Optional<Quiz> findById(long id);
    List<Quiz> findAll();
    @Query("SELECT q FROM Quiz q WHERE q.createdAt BETWEEN :startOfToday AND :endOfToday ORDER BY q.recommendation DESC")
    List<Quiz> findAllByTodayOrderByRecommendationDesc(LocalDateTime startOfToday, LocalDateTime endOfToday);

}
