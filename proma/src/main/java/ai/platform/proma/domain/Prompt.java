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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String promptTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String promptDescription;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String promptPreview;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String emoji;

    @Column(name = "prompt_category",nullable = false)
    @Enumerated(EnumType.STRING)
    private PromptCategory promptCategory;

    @Column
    @Enumerated(EnumType.STRING)
    private Scrap isScrap;

    @Column
    private Boolean isApis;

    @Column(length = 200)
    private String apisToken;

    @Column(length = 200)
    private String secretKey;

    // --------------------------------------------------------------------

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "promptMethod_id", nullable = false)
    private PromptMethods promptMethods;

    @OneToMany(mappedBy = "prompt", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<PromptBlock> promptBlocks;

    @OneToMany(mappedBy = "prompt", cascade = CascadeType.MERGE)
    private List<Post> posts;

    @OneToMany(mappedBy = "prompt", cascade = CascadeType.MERGE)
    private List<Message> messages;

    @Builder
    public Prompt(Long id, String promptTitle, String promptDescription, String promptPreview, PromptCategory promptCategory,String emoji, Scrap isScrap, User user, PromptMethods promptMethods) {
        this.id = id;
        this.promptTitle = promptTitle;
        this.promptDescription = promptDescription;
        this.promptPreview = promptPreview;
        this.promptCategory = promptCategory;
        this.emoji = emoji;
        this.isScrap = isScrap;
        this.user = user;
        this.promptMethods = promptMethods;
    }

    public static Prompt distributePrompt(Prompt prompt, User user) {
        return Prompt.builder()
                .promptTitle(prompt.getPromptTitle())
                .promptDescription(prompt.getPromptDescription())
                .promptPreview(prompt.getPromptPreview())
                .promptCategory(prompt.getPromptCategory())
                .emoji(prompt.getEmoji())
                .isScrap(Scrap.SHARED)
                .user(user)
                .promptMethods(prompt.getPromptMethods())
                .build();
    }

    public static Prompt scrapPost(Post post, User user) {
        return Prompt.builder()
                .promptTitle(post.getPrompt().getPromptTitle())
                .promptDescription(post.getPrompt().getPromptDescription())
                .promptPreview(post.getPrompt().getPromptPreview())
                .promptCategory(post.getPrompt().getPromptCategory())
                .emoji(post.getPrompt().getEmoji())
                .isScrap(Scrap.SCRAP)
                .user(user) // 스크랩하는 사용자
                .promptMethods(post.getPrompt().getPromptMethods())
                .build();
    }

    public void updateCategory(PromptCategory promptCategory){
        this.promptCategory = promptCategory;
    }

    public void updatePromptDetail(String promptTitle, String promptDescription, PromptCategory promptCategory, String promptPreview){
        this.promptTitle = promptTitle;
        this.promptDescription = promptDescription;
        this.promptCategory = promptCategory;
        this.promptPreview = promptPreview;
    }

    public void updateEmoji(String emoji){
        this.emoji = emoji;
    }
    public void updatePreview(String promptPreview){
        this.promptPreview = promptPreview;
    }
    public void updateApis(String apisToken, String secretKey){
        this.isApis = true;
        this.apisToken = apisToken;
        this.secretKey = secretKey;
    }
}
