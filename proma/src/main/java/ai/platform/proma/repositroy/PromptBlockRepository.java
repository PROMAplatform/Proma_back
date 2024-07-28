package ai.platform.proma.repositroy;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.PromptBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromptBlockRepository extends JpaRepository<PromptBlock, Long> {
    List<PromptBlock> findByPrompt(Prompt prompt);

    Optional<PromptBlock> findByPromptAndBlock(Prompt prompt, Block block);
}