package pl.sda.jg.ldz26java.voting.app;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "voters")
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voter_id")
    private Long id;

}
