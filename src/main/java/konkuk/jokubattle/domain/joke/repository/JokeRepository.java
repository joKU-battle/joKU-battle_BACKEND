package konkuk.jokubattle.domain.joke.repository;

import java.util.List;
import java.util.Optional;
import konkuk.jokubattle.domain.joke.entity.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
    Joke save(Joke joke);
    Optional<Joke> findById(long id);
    List<Joke> findAll();
    @Query("SELECT j FROM Joke j WHERE MONTH(j.createdAt) = :month AND YEAR(j.createdAt) = :year ORDER BY j.createdAt DESC")
    List<Joke> findAllByMonthAndYear(int month, int year);

    @Query(value = "select * from Joke order by random() limit 8", nativeQuery = true)
    List<Joke> findRandom8Data();
}
