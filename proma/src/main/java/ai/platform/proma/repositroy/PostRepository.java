package ai.platform.proma.repositroy;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p JOIN FETCH p.prompt pr " +
            "WHERE (:searchKeyword IS NULL OR p.postTitle LIKE %:searchKeyword% OR p.postDescription LIKE %:searchKeyword%) " +
            "AND (:category IS NULL OR pr.promptCategory = :category)")
    Page<Post> findAllBySearchKeywordAndCategory(
            @Param("searchKeyword") String searchKeyword,
            @Param("category") PromptCategory category,
            Pageable pageable
    );

}
