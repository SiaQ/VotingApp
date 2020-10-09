package pl.sda.jg.ldz26java.voting.app;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "voter_id")
    private Long voterId;

    @Column(name = "vote_for")
    private Long voteFor = 0L;

    @Column(name = "vote_against")
    private Long voteAgainst = 0L;

    public Vote(Long projectId, Long voterId, String vote) {
        this.projectId = projectId;
        this.voterId = voterId;
        if (vote.equalsIgnoreCase("for")) {
            this.voteFor += 1;
        } else {
            this.voteAgainst += 1;
        }
    }

    public Vote() {
    }
}
