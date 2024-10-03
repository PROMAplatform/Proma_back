package ai.platform.proma.repository;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PromptRepository extends JpaRepository<Prompt, Long> {

    Optional<Prompt> findByIdAndUser(Long promptId, User user);

    @Query("SELECT p FROM Prompt p WHERE p.user = :user AND p.isScrap IN (ai.platform.proma.domain.enums.Scrap.SCRAP, ai.platform.proma.domain.enums.Scrap.NOTSCRAP)")
    List<Prompt> findByUserAndScrap(@Param("user") User user);
}
