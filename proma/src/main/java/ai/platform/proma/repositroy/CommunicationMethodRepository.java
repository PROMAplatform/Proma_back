package ai.platform.proma.repositroy;
import ai.platform.proma.domain.CommunicationMethod;
import ai.platform.proma.domain.enums.PromptType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunicationMethodRepository extends JpaRepository<CommunicationMethod, Long> {
    Optional<CommunicationMethod> findByType(PromptType type);
}
