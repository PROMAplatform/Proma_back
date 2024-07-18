package ai.platform.proma.domain;

import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.domain.enums.Scrap;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "PROMPT_TB")
@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Prompt {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String promptTitle;

    @Column(nullable = false)
    private String promptDescription;

    @Column(nullable = false)
    private String promptPreview;

    @Column(name = "prompt_category",nullable = false)
    @Enumerated(EnumType.STRING)
    private PromptCategory promptCategory;

    @Column
    @Enumerated(EnumType.STRING)
    private Scrap isScrap;

    // --------------------------------------------------------------------

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "communicationMethod_id", nullable = false)
    private CommunicationMethod communicationMethod;

    @OneToMany(mappedBy = "prompt", cascade = CascadeType.MERGE)
    private List<PromptBlock> promptBlocks = new ArrayList<>();

    @OneToMany(mappedBy = "prompt", cascade = CascadeType.MERGE)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "prompt", cascade = CascadeType.MERGE)
    private List<Message> messages = new ArrayList<>();

    @Builder
    public Prompt(Long id, String promptTitle, String promptDescription, String promptPreview, PromptCategory promptCategory, Scrap isScrap, User user, CommunicationMethod communicationMethod) {
        this.id = id;
        this.promptTitle = promptTitle;
        this.promptDescription = promptDescription;
        this.promptPreview = promptPreview;
        this.promptCategory = promptCategory;
        this.isScrap = isScrap;
        this.user = user;
        this.communicationMethod = communicationMethod;
    }

    public static Prompt scrapPost(Post post, User user) {
        return Prompt.builder()
                .promptTitle(post.getPrompt().getPromptTitle())
                .promptDescription(post.getPrompt().getPromptDescription())
                .promptPreview(post.getPrompt().getPromptPreview())
                .promptCategory(post.getPrompt().getPromptCategory())
                .isScrap(Scrap.SCRAP)
                .user(user) // 스크랩하는 사용자
                .communicationMethod(post.getPrompt().getCommunicationMethod())
                .build();
    }
}
