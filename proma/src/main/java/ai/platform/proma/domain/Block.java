package ai.platform.proma.domain;

import jakarta.persistence.*;
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
}
