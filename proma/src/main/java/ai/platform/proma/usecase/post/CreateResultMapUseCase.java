package ai.platform.proma.usecase.post;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.SortInfo;
import org.springframework.data.domain.Page;

import java.util.Map;
@UseCase
public interface CreateResultMapUseCase {
    Map<String, Object> execute(Page<SortInfo> sortInfoPage, User user);

}
