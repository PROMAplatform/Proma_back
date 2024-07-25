package ai.platform.proma.controller;

import ai.platform.proma.domain.enums.PromptCategory;
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

    @GetMapping("/community")
    public ResponseDto<Map<String, Object>> getPostsBySearchKeyWord(
            @Valid @RequestParam(value = "userId", required = false) Long userId,
            @Valid @RequestParam(value = "search", required = false) String searchKeyword,
            @Valid @RequestParam(value = "category", required = false) String category,
            @Valid @RequestParam(value = "latest", defaultValue = "desc") String latestOrder,
            @Valid @RequestParam(value = "like", defaultValue = "desc") String likeOrder,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "9") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> postResponseDtos = postService.getPosts(userId, searchKeyword, category, pageable, likeOrder, latestOrder);

        Map<String, Object> response = new HashMap<>();
        response.put("selectPrompt", postResponseDtos.getContent());
        response.put("pageInfo", new PageInfo(postResponseDtos));

        return new ResponseDto<>(response);
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
        List<BlockResponseDto> BlockResponseDtos = postService.getPromptBlocksByPostId(postId);

        Map<String, Object> response = new HashMap<>();
        response.put("selectPromptAtom", BlockResponseDtos); // selectPromptAtom 키 사용

        return new ResponseDto<>(response);
    }

    @PostMapping("/community/like/{postId}")
    public ResponseDto<Boolean> postLike(@Valid @PathVariable("postId") Long postId) {

        return new ResponseDto<>(postService.postLike(postId));
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
        Page<PostResponseDto> postResponseDtos = postService.getPostsByUserLikes(userId, category, pageable, likeOrder, latestOrder);

        Map<String, Object> response = new HashMap<>();
        response.put("selectPrompt", postResponseDtos.getContent());
        response.put("pageInfo", new PageInfo(postResponseDtos));

        return new ResponseDto<>(response);
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
        Page<PostResponseDto> postResponseDtos = postService.getPostsByUserDistribute(userId, category, pageable, likeOrder, latestOrder);

        Map<String, Object> response = new HashMap<>();
        response.put("selectPrompt", postResponseDtos.getContent());
        response.put("pageInfo", new PageInfo(postResponseDtos));

        return new ResponseDto<>(response);
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
