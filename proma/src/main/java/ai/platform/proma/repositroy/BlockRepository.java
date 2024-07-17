package ai.platform.proma.repositroy;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    @Query("SELECT b FROM Block b WHERE b.user = :user OR b.user IS NULL")
    List<Block> findByUserOrUserIsNull(@Param("user") User user);

}
