package ai.platform.proma.repositroy;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.BlockCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    @Query("SELECT b FROM Block b WHERE b.user = :user OR b.user IS NULL AND b.promptMethods = :promptMethods")
    List<Block> findByUserOrUserIsNullAndPromptMethods(@Param("user") User user, @Param("promptMethods") PromptMethods promptMethods);

    Optional<Block> findByBlockValueAndBlockDescriptionAndBlockCategoryAndUserId(String title, String blockDescription, BlockCategory blockCategory, Long userId);

}
