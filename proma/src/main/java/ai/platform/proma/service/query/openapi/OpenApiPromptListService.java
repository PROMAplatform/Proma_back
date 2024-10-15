package ai.platform.proma.service.query.openapi;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.ApiPromptListResponseDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.PromptRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.openapi.OpenApiListOpenApiTokenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OpenApiPromptListService implements OpenApiListOpenApiTokenUseCase {

    private final PromptRepository promptRepository;
    private final UserRepository userRepository;

    @Override
    public Map<String, List<ApiPromptListResponseDto>> execute(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        List<Prompt> promptList = promptRepository.findAllByUserAndIsApisIsTrue(user);

        Map<String, List<ApiPromptListResponseDto>> responseDtos = new HashMap<>();

        responseDtos.put("apiList", promptList.stream().map(
                prompt -> ApiPromptListResponseDto.of(
                        prompt.getId(),
                        prompt.getPromptTitle(),
                        prompt.getApisToken(),
                        prompt.getSecretKey()
                )).collect(Collectors.toList()));

        return responseDtos;
    }
}
