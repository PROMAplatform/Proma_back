package ai.platform.proma.repositroy;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PromptRepository extends JpaRepository<Prompt, Long> {

    Optional<Prompt> findByIdAndUser(Long promptId, User user);

    List<Prompt> findByUser(User user);
}
