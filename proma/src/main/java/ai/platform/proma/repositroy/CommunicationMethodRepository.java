package ai.platform.proma.repositroy;
import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.enums.PromptMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunicationMethodRepository extends JpaRepository<PromptMethods, Long> {
    Optional<PromptMethods> findByPromptMethod(PromptMethod promptMethod);
}
