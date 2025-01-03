package konkuk.jokubattle.domain.user.repository;

import java.util.List;
import java.util.Optional;
import konkuk.jokubattle.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(String id);

    Optional<User> findById(String id);

    List<User> findAllByOrderByScoreDescCreatedAtAsc();
}
