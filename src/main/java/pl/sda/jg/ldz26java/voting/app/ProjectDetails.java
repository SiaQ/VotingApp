package pl.sda.jg.ldz26java.voting.app;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDetails {
    private String projectName;
    private String description;
    private boolean voteOpen;
    private Long votesForCount;
    private Long votesAgainstCount;

    public ProjectDetails(String projectName, String description, boolean voteOpen, Long votesForCount, Long votesAgainstCount) {
        this.projectName = projectName;
        this.description = description;
        this.voteOpen = voteOpen;
        this.votesForCount = votesForCount;
        this.votesAgainstCount = votesAgainstCount;
    }

    public ProjectDetails() {
    }

    @Override
    public String toString() {
        return "ProjectDetails{" +
                "projectName='" + projectName + '\'' +
                ", description='" + description + '\'' +
                ", voteOpen=" + voteOpen +
                ", votesForCount=" + votesForCount +
                ", votesAgainstCount=" + votesAgainstCount +
                '}';
    }
}
