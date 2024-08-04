package ai.platform.proma.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "CHATROOM_TB")
@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String chatRoomTitle;

    @Column(nullable = false)
    private LocalDate createAt;

    @Column(nullable = true)
    private String emoji;

// --------------------------------------------------------------------

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "chatRoom", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Message> messages;

    @Builder
    public ChatRoom(String chatRoomTitle, String emoji, User user) {
        this.chatRoomTitle = chatRoomTitle;
        this.createAt = LocalDate.now();
        this.emoji = emoji;
        this.user = user;
    }

    public void updateEmoji(String emoji) {
        this.emoji = emoji;
    }
}
