package ai.platform.proma.repositroy;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.enums.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptRepository extends JpaRepository<Prompt, Long> {
}
