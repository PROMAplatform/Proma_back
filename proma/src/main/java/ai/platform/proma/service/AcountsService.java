package ai.platform.proma.service;

import ai.platform.proma.domain.User;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repositroy.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AcountsService {

    private final UserRepository userRepository;


    public User getUserBySocialId(String socialId) {
        return userRepository.findBySocialIdAndRefreshTokenIsNotNullAndIsLoginIsTrue(socialId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
    }
}
