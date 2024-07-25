package ai.platform.proma.domain;

import ai.platform.proma.domain.enums.BlockCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "BLOCK_TB")
@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String blockValue;

    @Column(nullable = false)
    private String blockDescription;

    @Column(nullable = false)
    private BlockCategory blockCategory;

// --------------------------------------------------------------------

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "block", cascade = CascadeType.MERGE)
    private List<PromptBlock> PromptBlocks = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "prompt_methods_id", nullable = false)
    private PromptMethods promptMethods;


    @Builder
    public Block(Long id, String blockValue, String blockDescription, BlockCategory blockCategory, User user, PromptMethods promptMethods) {
        this.id = id;
        this.blockValue = blockValue;
        this.blockDescription = blockDescription;
        this.blockCategory = blockCategory;
        this.promptMethods = promptMethods;
        this.user = user;
    }
    public static Block scrapBlock(PromptBlock promptBlock, User user){
        return Block.builder()
                .blockValue(promptBlock.getBlock().getBlockValue())
                .blockDescription(promptBlock.getBlock().getBlockDescription())
                .blockCategory(promptBlock.getBlock().getBlockCategory())
                .user(user)
                .build();
    }



}
