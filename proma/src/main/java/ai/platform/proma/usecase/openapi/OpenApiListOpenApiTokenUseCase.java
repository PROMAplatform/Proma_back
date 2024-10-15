package ai.platform.proma.usecase.openapi;

import ai.platform.proma.dto.response.ApiPromptListResponseDto;

import java.util.List;
import java.util.Map;

public interface OpenApiListOpenApiTokenUseCase {
    Map<String, List<ApiPromptListResponseDto>> execute(Long userId);
}
