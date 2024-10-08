package ai.platform.proma.service.command.post;

import ai.platform.proma.domain.Post;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.PostRequestDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.PostRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.post.PostUpdatePostUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostUpdatePostService implements PostUpdatePostUseCase {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public Boolean updatePost(Long userId, Long postId, PostRequestDto postRequestDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorDefine.POST_NOT_FOUND));


        if (!post.getPrompt().getUser().getId().equals(user.getId())) {
            throw new ApiException(ErrorDefine.UNAUTHORIZED_USER);
        }
        post.update(postRequestDto.toEntity(postRequestDto));

        return true;
    }

}
