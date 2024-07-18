package ai.platform.proma.repositroy;

import ai.platform.proma.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    Optional<Block> findByTitleAndBlockDescriptionAndBlockCategoryAndUserId(String title, String blockDescription, String blockCategory, Long userId);

}
