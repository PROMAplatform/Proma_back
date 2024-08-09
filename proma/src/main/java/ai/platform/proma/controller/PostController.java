package ai.platform.proma.controller;

import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.request.PostDistributeRequestDto;
import ai.platform.proma.dto.request.PostRequestDto;
import ai.platform.proma.dto.response.*;
import ai.platform.proma.security.LoginUser;
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
            @LoginUser User user) {
        return new ResponseDto<>(postService.promptTitleList(user));
    }

    @GetMapping("/detail/{promptId}")
    public ResponseDto<PromptListResponseDto> promptDetail(
            @PathVariable("promptId") Long promptId,
            @LoginUser User user
    ) {
        return new ResponseDto<>(postService.promptDetail(promptId, user));
    }

    @PostMapping("/distribute/{promptId}")
    public ResponseDto<Boolean> distributePrompt(
            @Valid @PathVariable("promptId") Long promptId,
            @Valid @RequestBody PostDistributeRequestDto postDistributeRequestDto,
            @LoginUser User user    ) {
        return new ResponseDto<>(postService.distributePrompt(user, promptId, postDistributeRequestDto));
    }

    @GetMapping("")
    public ResponseDto<Map<String, Object>> getPostsBySearchKeyWord(
            @LoginUser User user,
            @Valid @RequestParam(value = "search", required = false) String searchKeyword,
            @Valid @RequestParam(value = "category", required = false) String category,
            @Valid @RequestParam(value = "like", required = false) String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ) {

        return new ResponseDto<>(postService.getPosts(user, searchKeyword, category, page, size, likeOrder));
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
            @LoginUser User user
    ) {
        return new ResponseDto<>(postService.scrapPrompt(postId, user));
    }

    @GetMapping("/block/{postId}")
    public ResponseDto<Map<String, Object>> getPromptBlocksByPostId(
            @Valid @PathVariable("postId") Long postId
    ) {
        return new ResponseDto<>(postService.getPromptBlocksByPostId(postId));
    }

    @PostMapping("/like/{postId}")
    public ResponseDto<Boolean> postLike(
            @Valid @PathVariable("postId") Long postId,
            @LoginUser User user    ) {
        return new ResponseDto<>(postService.postLike(postId, user));
    }
    @GetMapping("/my-like")
    public ResponseDto<Map<String, Object>> getPostsByUserLike(
            @LoginUser User user,
            @Valid @RequestParam(value = "category", required = false) String category,
            @Valid @RequestParam(value = "like", required = false) String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ){
        return new ResponseDto<>(postService.getPostsByUserLikes(user, category, page, size, likeOrder));
    }

    @GetMapping("/my-distribute")
    public ResponseDto<Map<String, Object>> getPostsByUserDistribute(
            @LoginUser User user,
            @Valid @RequestParam(value = "category", required = false) String category,
            @Valid @RequestParam(value = "like", required = false) String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ){

        return new ResponseDto<>(postService.getPostsByUserDistribute(user, category, page, size, likeOrder));
    }

    @PatchMapping("/my-distribute/patch/{postId}")
    public ResponseDto<Boolean> updatePost(@Valid @PathVariable("postId") Long postId,
                                     @Valid @RequestBody PostRequestDto requestDto,
                                           @LoginUser User user) {

        return new ResponseDto<>(postService.updatePost(user ,postId, requestDto));
    }

    @DeleteMapping("/my-distribute/delete/{postId}")
    public ResponseDto<Boolean> deletePost(@Valid @PathVariable("postId") Long postId,
                                           @LoginUser User user) {
        return new ResponseDto<>(postService.deletePost(user,postId));
    }


}
