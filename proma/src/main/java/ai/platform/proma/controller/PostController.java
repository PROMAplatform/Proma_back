package ai.platform.proma.controller;

import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.response.PageInfo;
import ai.platform.proma.dto.response.PostResponseDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/paging")
    public ResponseDto<Map<String, Object>> getCommunityPosts(
            @RequestParam(value = "search", required = false) String searchKeyword,
            @RequestParam(value = "category", required = false) PromptCategory category,
            @RequestParam(value = "latest", defaultValue = "desc") String latestOrder,
            @RequestParam(value = "like", defaultValue = "desc") String likeOrder,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> postResponseDtos = postService.getPosts(searchKeyword, category, pageable, likeOrder, latestOrder);

        Map<String, Object> response = new HashMap<>();
        response.put("selectPrompt", postResponseDtos.getContent());
        response.put("pageInfo", new PageInfo(postResponseDtos));

        return new ResponseDto<>(response);
    }

}
