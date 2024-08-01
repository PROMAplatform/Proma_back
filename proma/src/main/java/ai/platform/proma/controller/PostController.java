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
//@RequestMapping("/community")
public class PostController {

    private final PostService postService;

    @GetMapping("/community/titleList")
    public ResponseDto<Map<String, List<PromptTitleList>>> promptTitleList(
            @RequestParam(value = "userId") Long userId
    ) {
        return new ResponseDto<>(postService.promptTitleList(userId));
    }

    @GetMapping("/prompt/detail/{promptId}")
    public ResponseDto<PromptListResponseDto> promptDetail(
            @PathVariable("promptId") Long promptId,
            @RequestParam(value = "userId") Long userId
    ) {
        return new ResponseDto<>(postService.promptDetail(promptId, userId));
    }

    @PostMapping("/community/distribute/{promptId}")
    public ResponseDto<Boolean> distributePrompt(
            @Valid @PathVariable("promptId") Long promptId,
            @Valid @RequestBody PostDistributeRequestDto postDistributeRequestDto,
            @Valid @RequestParam(value = "userId", required = false) Long userId
    ) {
        return new ResponseDto<>(postService.distributePrompt(userId, promptId, postDistributeRequestDto));
    }

    @GetMapping("/community")
    public ResponseDto<Map<String, Object>> getPostsBySearchKeyWord(
            @Valid @RequestParam(value = "userId", required = false) Long userId,
            @Valid @RequestParam(value = "search", required = false) String searchKeyword,
            @Valid @RequestParam(value = "category", required = false) PromptCategory category,
            @Valid @RequestParam(value = "latest", defaultValue = "desc") String latestOrder,
            @Valid @RequestParam(value = "like", defaultValue = "desc") String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        return new ResponseDto<>(postService.getPosts(userId, searchKeyword, category, pageable, likeOrder, latestOrder));
    }

    @GetMapping("/community/preview")
    public ResponseDto<Map<String, Object>> getPostsPreview(
            @Valid @RequestParam(value = "search", required = false) String searchKeyword,
            @Valid @RequestParam(value = "category", required = false) PromptCategory category,
            @Valid @RequestParam(value = "latest", defaultValue = "desc") String latestOrder,
            @Valid @RequestParam(value = "like", defaultValue = "desc") String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        return new ResponseDto<>(postService.getPostsPreview(searchKeyword, category, pageable, likeOrder, latestOrder));
    }

    @PostMapping("/community/scrap/{postId}")
    public ResponseDto<Boolean> promptScrapByPostId(
            @Valid @PathVariable("postId") Long postId, // JWT사용시 수정 필요
            @Valid @RequestParam(value = "userId", required = false) Long userId
    ) {
        return new ResponseDto<>(postService.scrapPrompt(postId, userId));
    }

    @GetMapping("/community/detail/{postId}")
    public ResponseDto<Map<String, Object>> getPromptBlocksByPostId(
            @Valid @PathVariable("postId") Long postId
    ) {
        return new ResponseDto<>(postService.getPromptBlocksByPostId(postId));
    }

    @PostMapping("/community/like/{postId}")
    public ResponseDto<Boolean> postLike(@Valid @PathVariable("postId") Long postId,
                                         @Valid @RequestParam(value = "userId", required = false) Long userId
    ) {
        return new ResponseDto<>(postService.postLike(postId, userId));
    }
    @GetMapping("/community/my-like")
    public ResponseDto<Map<String, Object>> getPostsByUserLike(
            @Valid @RequestParam(value = "userId", required = false) Long userId, //JWT 수정필요
            @Valid @RequestParam(value = "category", required = false) PromptCategory category,
            @Valid @RequestParam(value = "latest", defaultValue = "desc") String latestOrder,
            @Valid @RequestParam(value = "like", defaultValue = "desc") String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseDto<>(postService.getPostsByUserLikes(userId, category, pageable, likeOrder, latestOrder));
    }

    @GetMapping("/community/my-distribute")
    public ResponseDto<Map<String, Object>> getPostsByUserDistribute(
            @Valid @RequestParam(value = "userId", required = false) Long userId, //JWT 수정필요
            @Valid @RequestParam(value = "category", required = false) PromptCategory category,
            @Valid @RequestParam(value = "latest", defaultValue = "desc") String latestOrder,
            @Valid @RequestParam(value = "like", defaultValue = "desc") String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseDto<>(postService.getPostsByUserDistribute(userId, category, pageable, likeOrder, latestOrder));
    }

    @PatchMapping("/community/my-distribute/patch/{postId}")
    public ResponseDto<Boolean> updatePost(@Valid @PathVariable("postId") Long postId,
                                     @Valid @RequestBody PostRequestDto requestDto,
                                     @Valid @RequestParam(value = "userId", required = false) Long userId) {

        return new ResponseDto<>(postService.updatePost(userId,postId, requestDto));
    }

    @DeleteMapping("/community/my-distribute/delete/{postId}")
    public ResponseDto<Boolean> deletePost(@Valid @PathVariable("postId") Long postId,
                                     @Valid @RequestParam(value = "userId", required = false) Long userId) {

        return new ResponseDto<>(postService.deletePost(userId,postId));
    }


}
