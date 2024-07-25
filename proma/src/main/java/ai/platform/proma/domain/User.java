package ai.platform.proma.domain;

import ai.platform.proma.domain.enums.UserLoginMethod;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "USER_TB")
@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userLoginId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserLoginMethod userLoginMethod;

    @Column(nullable = false)
    private LocalDate createAt;

    @Column(nullable = false)
    private Boolean userOngoing;

    // --------------------------------------------------------------------

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Prompt> prompts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Block> blocks = new ArrayList<>();

    @Builder
    public User(Long id, String userLoginId, String userName, UserLoginMethod userLoginMethod, LocalDate createAt, Boolean userOngoing) {
        this.id = id;
        this.userLoginId = userLoginId;
        this.userName = userName;
        this.userLoginMethod = userLoginMethod;
        this.createAt = createAt;
        this.userOngoing = userOngoing;
    }

    public void secession(){
        this.userOngoing = false;
    }

}
