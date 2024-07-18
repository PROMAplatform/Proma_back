package ai.platform.proma.repositroy;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.PromptBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromptBlockRepository extends JpaRepository<PromptBlock, Long> {
    List<PromptBlock> findByPrompt(Prompt prompt);
}