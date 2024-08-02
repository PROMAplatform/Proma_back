package ai.platform.proma.service;

import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.UserRequestDto;
import ai.platform.proma.dto.response.UserResponseDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repositroy.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    public final UserRepository userRepository;

    public UserResponseDto findById(UserRequestDto userRequestDto) {
        User user = userRepository.findById(userRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        return UserResponseDto.builder()
                .userName(user.getUserName())
                .build();
    }
    public Boolean userSecession(UserRequestDto userRequestDto){

        User user = userRepository.findById(userRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        user.secession();
        return true;
    }


}
