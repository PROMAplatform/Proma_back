package ai.platform.proma.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "MESSAGE_TB")
@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Message {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String messageQuestion;

    @Column(nullable = true)
    private String messageFile;

    @Column(nullable = false)
    private LocalDate messageCreateAt;

    @Column(nullable = false)
    private String messageAnswer;

    // --------------------------------------------------------------------

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "prompt_id", nullable = true)
    private Prompt prompt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatRoom;
}

