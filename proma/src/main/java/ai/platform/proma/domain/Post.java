package ai.platform.proma.domain;

import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.request.PostRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "POST_TB")
@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postDescription;

    @Column(nullable = false)
    private LocalDate createAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PromptCategory promptCategory;

// --------------------------------------------------------------------

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "prompt_id", nullable = false)
    private Prompt prompt;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Like> likes;

    @Builder
    public Post(String postTitle, String postDescription, PromptCategory promptCategory, Prompt prompt) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.createAt = LocalDate.now();
        this.promptCategory = promptCategory;
        this.prompt = prompt;
    }

    public void update(PostRequestDto postRequestDto) {
        this.postTitle = postRequestDto.getPostTitle();
        this.postDescription = postRequestDto.getPostDescription();
    }

}
