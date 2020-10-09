package pl.sda.jg.ldz26java.voting.app;

public class AlreadyVotedException extends Throwable {
    public AlreadyVotedException() {
        super("Already voted for that project!");
    }
}
