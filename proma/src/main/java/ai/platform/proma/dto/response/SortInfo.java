package ai.platform.proma.dto.response;

import ai.platform.proma.domain.Post;

public interface SortInfo {
    public Post getPost();
    public int getLikeCount();
}
