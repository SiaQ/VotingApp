package pl.sda.jg.ldz26java.voting.app;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    public static final VoteDAO VOTE_DAO = new VoteDAO();

    @GetMapping("/test")
    public String test() {
        return "Hello!";
    }

    @PostMapping("/load")
    public String loadDatabase() {
        VOTE_DAO.loadDatabase();
        return "Loaded";
    }

    @PostMapping("/vote")
    public String vote(
            @RequestParam("projectId") Long projectId,
            @RequestParam("voterId") Long voterId,
            @RequestParam("vote") String vote) {
        return VOTE_DAO.voteForProject(projectId, voterId, vote);
    }

    @PutMapping("/closeVoting")
    public String closeVoting(@RequestParam("projectId") Long projectId) {
        return VOTE_DAO.closeVoting(projectId);
    }

    @GetMapping("/projectDetails")
    public ProjectDetails projectDetails(@RequestParam("projectId") Long projectId) {
        return VOTE_DAO.projectDetails(projectId);
    }

    @GetMapping("/getProjectsList")
    public List<Project> returnProjectsList() {
        return VOTE_DAO.projectsList();
    }
}
