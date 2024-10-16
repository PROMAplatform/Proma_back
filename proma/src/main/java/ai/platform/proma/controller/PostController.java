package ai.platform.proma.controller;

import ai.platform.proma.dto.request.PostDistributeRequestDto;
import ai.platform.proma.dto.request.PostRequestDto;
import ai.platform.proma.dto.response.*;
import ai.platform.proma.annotation.LoginUser;
import ai.platform.proma.usecase.post.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostGetPostsUseCase postGetPostsUseCase;
    private final PostScrapPromptUseCase postScrapPromptUseCase;
    private final PostLikeUseCase postLikeUseCase;
    private final PostGetPostsByUserLikesUseCase postGetPostsByUserLikesUseCase;
    private final PostGetPostsByUserDistributeUseCase postGetPostsByUserDistributeUseCase;
    private final PostUpdatePostUseCase postUpdatePostUseCase;
    private final PostDeletePostUseCase postDeletePostUseCase;


    @GetMapping("")
    public ResponseDto<Map<String, Object>> getPostsBySearchKeyWord(
            @LoginUser Long userId,
            @RequestParam(value = "search", required = false) String searchKeyword,
            @RequestParam(value = "method", required = false) String method,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "like", required = false) String likeOrder,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size
    ) {

        return new ResponseDto<>(postGetPostsUseCase.getPosts(userId, searchKeyword, category, page, size, likeOrder, method));
    }

    @PostMapping("/{postId}") //스크랩
    public ResponseDto<Boolean> promptScrapByPostId(
            @PathVariable("postId") Long postId, // JWT사용시 수정 필요
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(postScrapPromptUseCase.scrapPrompt(postId, userId));
    }


    @PostMapping("/{postId}/likes")
    public ResponseDto<Boolean> postLike(
            @PathVariable("postId") Long postId,
            @LoginUser Long userId    ) {
        return new ResponseDto<>(postLikeUseCase.postLike(postId, userId));
    }
    @GetMapping("/likes")
    public ResponseDto<Map<String, Object>> getPostsByUserLike(
            @LoginUser Long userId,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "method", required = false) String method,
            @RequestParam(value = "like", required = false) String likeOrder,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size
    ){
        return new ResponseDto<>(postGetPostsByUserLikesUseCase.getPostsByUserLikes(userId, category, page, size, likeOrder, method));
    }

    @GetMapping("/distributed")
    public ResponseDto<Map<String, Object>> getPostsByUserDistribute(
            @LoginUser Long userId,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "method", required = false) String method,
            @RequestParam(value = "like", required = false) String likeOrder,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size
    ){

        return new ResponseDto<>(postGetPostsByUserDistributeUseCase.getPostsByUserDistribute(userId, category, page, size, likeOrder, method));
    }


    @PatchMapping("/{postId}")
    public ResponseDto<Boolean> updatePost(
            @PathVariable("postId") Long postId,
            @Valid @RequestBody PostRequestDto requestDto,
            @LoginUser Long userId) {

        return new ResponseDto<>(postUpdatePostUseCase.updatePost(userId ,postId, requestDto));
    }

    @DeleteMapping("/{postId}")
    public ResponseDto<Boolean> deletePost(@PathVariable("postId") Long postId,
                                           @LoginUser Long userId) {
        return new ResponseDto<>(postDeletePostUseCase.deletePost(userId,postId));
    }


}
