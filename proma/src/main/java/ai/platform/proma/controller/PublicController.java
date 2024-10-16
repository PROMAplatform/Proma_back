package ai.platform.proma.controller;

import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.usecase.post.PostGetPostsPreviewUseCase;
import ai.platform.proma.usecase.post.PostGetPromptBlocksByPostIdUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {

    private final PostGetPostsPreviewUseCase postGetPostsPreviewUseCase;
    private final PostGetPromptBlocksByPostIdUseCase postGetPromptBlocksByPostIdUseCase;


    @GetMapping("/posts") //public으로 변경
    public ResponseDto<Map<String, Object>> getPostsPreview(
            @RequestParam(value = "search", required = false) String searchKeyword,
            @RequestParam(value = "method", required = false) String method,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "like", required = false) String likeOrder,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size
    ) {

        return new ResponseDto<>(postGetPostsPreviewUseCase.getPostsPreview(searchKeyword, category, page, size, likeOrder, method));
    }

    @GetMapping("/posts/{postId}") //public으로 변경
    public ResponseDto<Map<String, Object>> getPromptBlocksByPostId(
            @PathVariable("postId") Long postId
    ) {
        return new ResponseDto<>(postGetPromptBlocksByPostIdUseCase.getPromptBlocksByPostId(postId));
    }
}
