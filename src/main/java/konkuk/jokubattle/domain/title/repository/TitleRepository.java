package konkuk.jokubattle.domain.title.repository;

import java.util.Optional;
import konkuk.jokubattle.domain.title.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TitleRepository extends JpaRepository<Title, Long> {

    @Query("select ti from Title ti order by ti.createdAt desc limit 1")
    Optional<Title> findRecentTitleByUsIdx(Long usIdx);
}
