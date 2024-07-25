package ai.platform.proma.domain;

import jakarta.persistence.*;
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

// --------------------------------------------------------------------

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "prompt_id", nullable = false)
    private Prompt prompt;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Like> likes = new ArrayList<>();

    public void update(String postTitle, String postDescription) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
    }

}
