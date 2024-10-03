package ai.platform.proma.service.command.post;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.PostRequestDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.PostRepository;
import ai.platform.proma.usecase.post.PostUpdatePostUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostUpdatePostService implements PostUpdatePostUseCase {

    private final PostRepository postRepository;
    public Boolean updatePost(User user, Long postId, PostRequestDto postRequestDto){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));

        Long userId = user.getId();

        if (!post.getPrompt().getUser().getId().equals(userId)) {
            throw new ApiException(ErrorDefine.UNAUTHORIZED_USER);
        }
        post.update(postRequestDto.toEntity(postRequestDto));

        return true;
    }

}
