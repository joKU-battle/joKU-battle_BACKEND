package konkuk.jokubattle.domain.joke.repository;

import konkuk.jokubattle.domain.joke.entity.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
    Joke save(Joke joke);
    Optional<Joke> findById(long id);
    List<Joke> findAll();
    @Query("SELECT j FROM Joke j WHERE MONTH(j.createdAt) = :month AND YEAR(j.createdAt) = :year ORDER BY j.createdAt DESC")
    List<Joke> findAllByMonthAndYear(int month, int year);
}
