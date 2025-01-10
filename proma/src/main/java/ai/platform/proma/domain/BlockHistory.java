package ai.platform.proma.domain;

import static jakarta.persistence.FetchType.LAZY;

import ai.platform.proma.domain.enums.PromptMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "BLOCK_HISTORY_TB")
@Entity
@Getter
@NoArgsConstructor
public class BlockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String history;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PromptMethod promptMethod;

    @Column(nullable = false)
    private String promptCategory;

    //------------------------------------

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //------------------------------------
    @Builder
    public BlockHistory(String history, PromptMethod promptMethod, String promptCategory, User user) {
        this.history = history;
        this.promptMethod = promptMethod;
        this.promptCategory = promptCategory;
        this.user = user;
    }
}
