package ai.platform.proma.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "PROMPT_BLOCK_TB")
@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class PromptBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

// --------------------------------------------------------------------

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "prompt_id", nullable = false)
    private Prompt prompt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "block_id", nullable = false)
    private Block block;

    @Builder
    public PromptBlock(Long id, Prompt prompt, Block block) {
        this.id = id;
        this.prompt = prompt;
        this.block = block;
    }
    public static PromptBlock scrapPromptBlock(Prompt prompt, Block block) {
        return PromptBlock.builder()
                .prompt(prompt) // 새로 생성된 Prompt 엔티티 연결
                .block(block) // 새로 생성된 Block 엔티티 연결
                .build();
    }
}
