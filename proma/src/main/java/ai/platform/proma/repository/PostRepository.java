package ai.platform.proma.repository;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.enums.PromptCategory;

import ai.platform.proma.dto.response.SortInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p AS post, COALESCE(COUNT(l.id), 0) AS likeCount " +
            "FROM Post p " +
            "LEFT JOIN Like l ON p.id = l.post.id " +
            "LEFT JOIN FETCH Prompt pr ON p.prompt.id = pr.id " +
            "WHERE (:searchKeyword IS NULL OR p.postTitle LIKE %:searchKeyword% OR p.postDescription LIKE %:searchKeyword%) " +
            "AND (:category IS NULL OR p.postCategory = :category)"+
            "AND (:method IS NULL OR pr.promptMethods = :method)"+
            "GROUP BY p")
    Page<SortInfo> findAllBySearchKeywordAndCategory( // 게시글 미리보기 로그인 x
            @Param("searchKeyword") String searchKeyword,
            @Param("category") PromptCategory category,
            @Param("method") PromptMethods method,
            Pageable pageable
    );

    @Query("SELECT p AS post, COALESCE(COUNT(l.id), 0) AS likeCount " +
            "FROM Post p JOIN FETCH p.prompt pr " +
            "LEFT JOIN Like l ON p.id = l.post.id " +
            "WHERE (pr.user.id = :userId) " +
            "AND (:category IS NULL OR p.postCategory = :category) " +
            "AND (:method IS NULL OR pr.promptMethods = :method)"+
            "AND pr.isScrap IN (ai.platform.proma.domain.enums.Scrap.SHARED)"+
            "GROUP BY p")
    Page<SortInfo> findAllByUserIdAndPostCategoryAndIsScrapShared( // 내가 공유한 프롬프트 조회
            @Param("userId") Long userId,
            @Param("category") PromptCategory promptCategory,
            @Param("method") PromptMethods method,
            Pageable pageable);

    @Query("SELECT p AS post, COALESCE(COUNT(l.id), 0) AS likeCount " +
            "FROM Post p " +
            "LEFT JOIN FETCH Prompt pr ON p.prompt.id = pr.id " +
            "LEFT JOIN Like l ON p.id = l.post.id " +
            "WHERE p.id IN :postIds " +
            "AND (:category IS NULL OR p.postCategory = :category)" +
            "AND (:method IS NULL OR pr.promptMethods = :method)"+
            "GROUP BY p")
    Page<SortInfo> findAllByPostIdInAndPostCategory( // 좋아요 한 게시글 리스트 조회
           @Param("category") PromptCategory category,
           @Param("method") PromptMethods method,
           @Param("postIds") List<Long> postIds,
           Pageable pageable
    );

}
