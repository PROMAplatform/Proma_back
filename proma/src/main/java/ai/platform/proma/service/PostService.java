package ai.platform.proma.service;
import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.response.PostResponseDto;
import ai.platform.proma.repositroy.LikeRepository;
import ai.platform.proma.repositroy.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    public Page<PostResponseDto> getPosts(String searchKeyword, PromptCategory category, Pageable pageable, String likeOrder, String latestOrder) {
        Page<Post> posts = postRepository.findAllBySearchKeywordAndCategory(searchKeyword, category, pageable);

        List<PostResponseDto> sortedPostResponseDtos = posts.stream() // posts를 Stream으로 변환
                .map(post -> new PostResponseDto(post, likeRepository.countByPostId(post.getId())))
                .sorted((dto1, dto2) -> {
                    int likeCountComparison = Sort.Direction.fromString(likeOrder).isAscending()
                            ? Integer.compare(dto1.getLikeCount(), dto2.getLikeCount())
                            : Integer.compare(dto2.getLikeCount(), dto1.getLikeCount());

                    if (likeCountComparison == 0) {
                        return Sort.Direction.fromString(latestOrder).isAscending()
                                ? dto1.getCreateAt().compareTo(dto2.getCreateAt())
                                : dto2.getCreateAt().compareTo(dto1.getCreateAt());
                    }
                    return likeCountComparison;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(sortedPostResponseDtos, pageable, posts.getTotalElements());
    }



}
