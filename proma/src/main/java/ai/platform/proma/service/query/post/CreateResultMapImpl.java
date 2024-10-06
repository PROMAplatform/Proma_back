package ai.platform.proma.service.query.post;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.PageInfo;
import ai.platform.proma.dto.response.PostResponseDto;
import ai.platform.proma.dto.response.SortInfo;
import ai.platform.proma.repository.LikeRepository;
import ai.platform.proma.usecase.post.CreateResultMapUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateResultMapImpl implements CreateResultMapUseCase {
    private final LikeRepository likeRepository;

    public Map<String, Object> execute(Page<SortInfo> sortInfoPage, User user) {

        List<Post> posts = sortInfoPage.getContent().stream()
                .map(SortInfo::getPost)
                .collect(Collectors.toList());

        List<Long> likedPostIds = likeRepository.findLikedPostIdsByPostsAndUser(posts, user);

        Map<Post, Boolean> likedPostsMap = posts.stream()
                .collect(Collectors.toMap(post -> post, post -> likedPostIds.contains(post.getId())));

        List<PostResponseDto> responseDtos = sortInfoPage.getContent().stream()
                .map(sortInfo -> PostResponseDto.of(sortInfo, likedPostsMap.getOrDefault(sortInfo.getPost(), false)))
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("selectPrompt", responseDtos);
        result.put("pageInfo", new PageInfo(sortInfoPage));

        return result;
    }
}
