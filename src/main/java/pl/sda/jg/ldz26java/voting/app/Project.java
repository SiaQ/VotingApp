package pl.sda.jg.ldz26java.voting.app;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "project_name")
    private String name;

    @Column(name = "project_description")
    private String description;

    @Column(name = "open_vote")
    private boolean voteOpen = true;

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Project() {
    }
}
