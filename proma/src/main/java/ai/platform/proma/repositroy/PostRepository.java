package ai.platform.proma.repositroy;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.PromptCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p JOIN FETCH p.prompt pr " +
            "WHERE (:searchKeyword IS NULL OR p.postTitle LIKE %:searchKeyword% OR p.postDescription LIKE %:searchKeyword%) " +
            "AND (:category IS NULL OR p.postCategory = :category)")
    Page<Post> findAllBySearchKeywordAndCategory(
            @Param("searchKeyword") String searchKeyword,
            @Param("category") PromptCategory category,
            Pageable pageable
    );
    @Query("SELECT p FROM Post p JOIN FETCH p.prompt pr " +
            "WHERE (:searchKeyword IS NULL OR p.postTitle LIKE %:searchKeyword% OR p.postDescription LIKE %:searchKeyword%) " +
            "AND (:category IS NULL OR p.postCategory = :category)")
    Page<Post> findAllBySearchKeywordAndCategoryOrderByCreateAtDesc(
            @Param("searchKeyword") String searchKeyword,
            @Param("category") PromptCategory category,
            Pageable pageable
    );
    @Query("SELECT p FROM Post p JOIN FETCH p.prompt pr " +
            "WHERE (:searchKeyword IS NULL OR p.postTitle LIKE %:searchKeyword% OR p.postDescription LIKE %:searchKeyword%) " +
            "AND (:category IS NULL OR p.postCategory = :category)")
    Page<Post> findAllBySearchKeywordAndCategoryOrderByLikesDesc(
            @Param("searchKeyword") String searchKeyword,
            @Param("category") PromptCategory category,
            Pageable pageable
    );

    @Query("SELECT p FROM Post p JOIN FETCH p.prompt pr " +
            "WHERE p.id IN :postIds " +
            "AND (:category IS NULL OR p.postCategory = :category)")
    Page<Post> findAllByPostIdInAndPostCategory( // 메서드 이름 변경
           @Param("category") PromptCategory category,
           @Param("postIds")List<Long> postIds,
           Pageable pageable
    );

    @Query("SELECT p FROM Post p JOIN FETCH p.prompt pr " +
            "WHERE pr.user.id = :userId " +
            "AND (:category IS NULL OR p.postCategory = :category) " +
            "AND pr.isScrap IN (ai.platform.proma.domain.enums.Scrap.SHARED)") // p.isScrap = SHARED 조건 추가
    Page<Post> findAllByPromptUserIdAndPostCategoryAndIsScrapShared( // 메서드 이름 변경
         @Param("userId") Long userId,
         @Param("category") PromptCategory category, // category 파라미터 추가
         Pageable pageable
    );
}
