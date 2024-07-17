package ai.platform.proma.repositroy;
import ai.platform.proma.domain.CommunicationMethod;
import ai.platform.proma.domain.enums.PromptType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunicationMethodRepository extends JpaRepository<CommunicationMethod, Long> {
    Optional<CommunicationMethod> findByType(PromptType type);
}
