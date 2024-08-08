package ai.platform.proma.controller;

import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.request.PostDistributeRequestDto;
import ai.platform.proma.dto.request.PostRequestDto;
import ai.platform.proma.dto.response.*;
import ai.platform.proma.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/community")
public class PostController {

    private final PostService postService;

    @GetMapping("/titleList")
    public ResponseDto<Map<String, List<PromptTitleList>>> promptTitleList(
            @RequestParam(value = "userId") Long userId
    ) {
        return new ResponseDto<>(postService.promptTitleList(userId));
    }

    @GetMapping("/detail/{promptId}")
    public ResponseDto<PromptListResponseDto> promptDetail(
            @PathVariable("promptId") Long promptId,
            @RequestParam(value = "userId") Long userId
    ) {
        return new ResponseDto<>(postService.promptDetail(promptId, userId));
    }

    @PostMapping("/distribute/{promptId}")
    public ResponseDto<Boolean> distributePrompt(
            @Valid @PathVariable("promptId") Long promptId,
            @Valid @RequestBody PostDistributeRequestDto postDistributeRequestDto,
            @Valid @RequestParam(value = "userId", required = false) Long userId
    ) {
        return new ResponseDto<>(postService.distributePrompt(userId, promptId, postDistributeRequestDto));
    }

    @GetMapping("")
    public ResponseDto<Map<String, Object>> getPostsBySearchKeyWord(
            @Valid @RequestParam(value = "userId", required = false) Long userId,
            @Valid @RequestParam(value = "search", required = false) String searchKeyword,
            @Valid @RequestParam(value = "category", required = false) String category,
            @Valid @RequestParam(value = "like", required = false) String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ) {

        return new ResponseDto<>(postService.getPosts(userId, searchKeyword, category, page, size, likeOrder));
    }

    @GetMapping("/preview")
    public ResponseDto<Map<String, Object>> getPostsPreview(
            @Valid @RequestParam(value = "search", required = false) String searchKeyword,
            @Valid @RequestParam(value = "category", required = false) String category,
            @Valid @RequestParam(value = "like", required = false) String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ) {

        return new ResponseDto<>(postService.getPostsPreview(searchKeyword, category, page, size, likeOrder));
    }

    @PostMapping("/scrap/{postId}")
    public ResponseDto<Boolean> promptScrapByPostId(
            @Valid @PathVariable("postId") Long postId, // JWT사용시 수정 필요
            @Valid @RequestParam(value = "userId", required = false) Long userId
    ) {
        return new ResponseDto<>(postService.scrapPrompt(postId, userId));
    }

    @GetMapping("/block/{postId}")
    public ResponseDto<Map<String, Object>> getPromptBlocksByPostId(
            @Valid @PathVariable("postId") Long postId
    ) {
        return new ResponseDto<>(postService.getPromptBlocksByPostId(postId));
    }

    @PostMapping("/like/{postId}")
    public ResponseDto<Boolean> postLike(@Valid @PathVariable("postId") Long postId,
                                         @Valid @RequestParam(value = "userId", required = false) Long userId
    ) {
        return new ResponseDto<>(postService.postLike(postId, userId));
    }
    @GetMapping("/my-like")
    public ResponseDto<Map<String, Object>> getPostsByUserLike(
            @Valid @RequestParam(value = "userId", required = false) Long userId, //JWT 수정필요
            @Valid @RequestParam(value = "category", required = false) String category,
            @Valid @RequestParam(value = "like", required = false) String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ){
        return new ResponseDto<>(postService.getPostsByUserLikes(userId, category, page, size, likeOrder));
    }

    @GetMapping("/my-distribute")
    public ResponseDto<Map<String, Object>> getPostsByUserDistribute(
            @Valid @RequestParam(value = "userId", required = false) Long userId, //JWT 수정필요
            @Valid @RequestParam(value = "category", required = false) String category,
            @Valid @RequestParam(value = "like", required = false) String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ){

        return new ResponseDto<>(postService.getPostsByUserDistribute(userId, category, page, size, likeOrder));
    }

    @PatchMapping("/my-distribute/patch/{postId}")
    public ResponseDto<Boolean> updatePost(@Valid @PathVariable("postId") Long postId,
                                     @Valid @RequestBody PostRequestDto requestDto,
                                     @Valid @RequestParam(value = "userId", required = false) Long userId) {

        return new ResponseDto<>(postService.updatePost(userId,postId, requestDto));
    }

    @DeleteMapping("/my-distribute/delete/{postId}")
    public ResponseDto<Boolean> deletePost(@Valid @PathVariable("postId") Long postId,
                                     @Valid @RequestParam(value = "userId", required = false) Long userId) {

        return new ResponseDto<>(postService.deletePost(userId,postId));
    }


}
