package ai.platform.proma.domain;

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
    private String title;

    @Column(nullable = false)
    private String blockDescription;

    @Column(nullable = false)
    private String blockCategory;

// --------------------------------------------------------------------

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "block", cascade = CascadeType.MERGE)
    private List<PromptBlock> PromptBlocks = new ArrayList<>();


    @Builder
    public Block(Long id, String title, String blockDescription, String blockCategory, User user) {
        this.id = id;
        this.title = title;
        this.blockDescription = blockDescription;
        this.blockCategory = blockCategory;
        this.user = user;
    }
    public static Block scrapBlock(PromptBlock promptBlock, User user){
        return Block.builder()
                .title(promptBlock.getBlock().getTitle())
                .blockDescription(promptBlock.getBlock().getBlockDescription())
                .blockCategory(promptBlock.getBlock().getBlockCategory())
                .user(user)
                .build();
    }



}
