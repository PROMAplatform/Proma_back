package ai.platform.proma.domain;

import ai.platform.proma.domain.enums.PromptMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "PROMPT_TYPE_TB")
@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class PromptMethods {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PromptMethod promptMethod;

    // --------------------------------------------------------------------

    @OneToMany(mappedBy = "promptMethods", cascade = CascadeType.MERGE)
    private List<Prompt> prompts;

    @OneToMany(mappedBy = "promptMethods", cascade = CascadeType.MERGE)
    private List<Block> Blocks;


}
