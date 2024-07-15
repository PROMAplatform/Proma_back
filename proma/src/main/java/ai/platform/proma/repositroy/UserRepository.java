package ai.platform.proma.repositroy;

import ai.platform.proma.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
