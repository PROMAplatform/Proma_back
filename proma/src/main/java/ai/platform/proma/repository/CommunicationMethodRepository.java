package ai.platform.proma.repository;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.enums.PromptMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunicationMethodRepository extends JpaRepository<PromptMethods, Long> {

    Optional<PromptMethods> findByPromptMethod(PromptMethod promptMethod);
    Optional<PromptMethods> findByPrompts(List<Prompt> prompts);
    Optional<PromptMethods> findById(Long id);
}
