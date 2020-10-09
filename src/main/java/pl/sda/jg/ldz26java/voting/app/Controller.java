package pl.sda.jg.ldz26java.voting.app;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @GetMapping("/test")
    public String test() {
        return "Hello!";
    }

    @PostMapping("/load")
    public String loadDatabase() {
        final VoteDAO voteDAO = new VoteDAO();
        voteDAO.loadDatabase();
        return "Loaded";
    }

    @PostMapping("/vote")
    public String vote(
            @RequestParam("projectId") Long projectId,
            @RequestParam("voterId") Long voterId,
            @RequestParam("vote") String vote) {
        final VoteDAO voteDAO = new VoteDAO();
        return voteDAO.voteForProject(projectId, voterId, vote);
    }

    @PutMapping("/closeVoting")
    public String closeVoting(@RequestParam("projectId") Long projectId) {
        final VoteDAO voteDAO = new VoteDAO();
        return voteDAO.closeVoting(projectId);
    }

    @GetMapping("/projectDetails")
    public ProjectDetails projectDetails(@RequestParam("projectId") Long projectId) {
        final VoteDAO voteDAO = new VoteDAO();
        return voteDAO.projectDetails(projectId);
    }
}
