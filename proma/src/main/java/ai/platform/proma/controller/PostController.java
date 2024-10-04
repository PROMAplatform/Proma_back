package ai.platform.proma.controller;

import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.PostDistributeRequestDto;
import ai.platform.proma.dto.request.PostRequestDto;
import ai.platform.proma.dto.response.*;
import ai.platform.proma.annotation.LoginUser;
import ai.platform.proma.service.PostService;
import ai.platform.proma.service.query.post.PostGetPostsPreviewService;
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
@RequestMapping("/community")
public class PostController {

    private final PostService postService;
    private final PostPromptTitleListUseCase postPromptTitleListUseCase;
    private final PostPromptDetailUseCase postPromptDetailUseCase;
    private final PostDistributePromptUseCase postDistributePromptUseCase;
    private final PostGetPostsUseCase postGetPostsUseCase;
    private final PostGetPostsPreviewUseCase postGetPostsPreviewUseCase;
    private final PostScrapPromptUseCase postScrapPromptUseCase;
    private final PostGetPromptBlocksByPostIdUseCase postGetPromptBlocksByPostIdUseCase;
    private final PostLikeUseCase postLikeUseCase;
    private final PostGetPostsByUserLikesUseCase postGetPostsByUserLikesUseCase;
    private final PostGetPostsByUserDistributeUseCase postGetPostsByUserDistributeUseCase;
    private final PostUpdatePostUseCase postUpdatePostUseCase;
    private final PostDeletePostUseCase postDeletePostUseCase;

    @GetMapping("/titleList")
    public ResponseDto<Map<String, List<PromptTitleList>>> promptTitleList(
            @LoginUser User user) {
        return new ResponseDto<>(postPromptTitleListUseCase.promptTitleList(user));
    }

    @GetMapping("/detail/{promptId}")
    public ResponseDto<PromptListResponseDto> promptDetail(
            @PathVariable("promptId") Long promptId,
            @LoginUser User user
    ) {
        return new ResponseDto<>(postPromptDetailUseCase.promptDetail(promptId, user));
    }

    @PostMapping("/distribute/{promptId}")
    public ResponseDto<Boolean> distributePrompt(
            @Valid @PathVariable("promptId") Long promptId,
            @Valid @RequestBody PostDistributeRequestDto postDistributeRequestDto,
            @LoginUser User user    ) {
        return new ResponseDto<>(postDistributePromptUseCase.distributePrompt(user, promptId, postDistributeRequestDto));
    }

    @GetMapping("")
    public ResponseDto<Map<String, Object>> getPostsBySearchKeyWord(
            @LoginUser User user,
            @Valid @RequestParam(value = "search", required = false) String searchKeyword,
            @Valid @RequestParam(value = "method", required = false) String method,
            @Valid @RequestParam(value = "category", required = false) String category,
            @Valid @RequestParam(value = "like", required = false) String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ) {

        return new ResponseDto<>(postGetPostsUseCase.getPosts(user, searchKeyword, category, page, size, likeOrder, method));
    }

    @GetMapping("/preview")
    public ResponseDto<Map<String, Object>> getPostsPreview(
            @Valid @RequestParam(value = "search", required = false) String searchKeyword,
            @Valid @RequestParam(value = "method", required = false) String method,
            @Valid @RequestParam(value = "category", required = false) String category,
            @Valid @RequestParam(value = "like", required = false) String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ) {

        return new ResponseDto<>(postGetPostsPreviewUseCase.getPostsPreview(searchKeyword, category, page, size, likeOrder, method));
    }

    @PostMapping("/scrap/{postId}")
    public ResponseDto<Boolean> promptScrapByPostId(
            @Valid @PathVariable("postId") Long postId, // JWT사용시 수정 필요
            @LoginUser User user
    ) {
        return new ResponseDto<>(postScrapPromptUseCase.scrapPrompt(postId, user));
    }

    @GetMapping("/block/{postId}")
    public ResponseDto<Map<String, Object>> getPromptBlocksByPostId(
            @Valid @PathVariable("postId") Long postId
    ) {
        return new ResponseDto<>(postGetPromptBlocksByPostIdUseCase.getPromptBlocksByPostId(postId));
    }

    @PostMapping("/like/{postId}")
    public ResponseDto<Boolean> postLike(
            @Valid @PathVariable("postId") Long postId,
            @LoginUser User user    ) {
        return new ResponseDto<>(postLikeUseCase.postLike(postId, user));
    }
    @GetMapping("/my-like")
    public ResponseDto<Map<String, Object>> getPostsByUserLike(
            @LoginUser User user,
            @Valid @RequestParam(value = "category", required = false) String category,
            @Valid @RequestParam(value = "method", required = false) String method,
            @Valid @RequestParam(value = "like", required = false) String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ){
        return new ResponseDto<>(postGetPostsByUserLikesUseCase.getPostsByUserLikes(user, category, page, size, likeOrder, method));
    }

    @GetMapping("/my-distribute")
    public ResponseDto<Map<String, Object>> getPostsByUserDistribute(
            @LoginUser User user,
            @Valid @RequestParam(value = "category", required = false) String category,
            @Valid @RequestParam(value = "method", required = false) String method,
            @Valid @RequestParam(value = "like", required = false) String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ){

        return new ResponseDto<>(postGetPostsByUserDistributeUseCase.getPostsByUserDistribute(user, category, page, size, likeOrder, method));
    }

    @PatchMapping("/my-distribute/patch/{postId}")
    public ResponseDto<Boolean> updatePost(@Valid @PathVariable("postId") Long postId,
                                     @Valid @RequestBody PostRequestDto requestDto,
                                           @LoginUser User user) {

        return new ResponseDto<>(postUpdatePostUseCase.updatePost(user ,postId, requestDto));
    }

    @DeleteMapping("/my-distribute/delete/{postId}")
    public ResponseDto<Boolean> deletePost(@Valid @PathVariable("postId") Long postId,
                                           @LoginUser User user) {
        return new ResponseDto<>(postDeletePostUseCase.deletePost(user,postId));
    }


}
