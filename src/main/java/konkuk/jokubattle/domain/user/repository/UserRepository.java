package konkuk.jokubattle.domain.user.repository;

import konkuk.jokubattle.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(String id);
}
