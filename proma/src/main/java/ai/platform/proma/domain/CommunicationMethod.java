package ai.platform.proma.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Table(name = "CommuMethod_TB")
@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class CommunicationMethod {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    // --------------------------------------------------------------------

    @OneToMany(mappedBy = "communicationMethod", cascade = CascadeType.MERGE)
    private List<Prompt> prompts = new ArrayList<>();


}
