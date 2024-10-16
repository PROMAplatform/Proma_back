package ai.platform.proma.usecase.post;
import ai.platform.proma.annotation.UseCase;
import org.springframework.data.domain.Sort;
@UseCase
public interface SortOrderUseCase {
    Sort execute(String likeOrder);
}
