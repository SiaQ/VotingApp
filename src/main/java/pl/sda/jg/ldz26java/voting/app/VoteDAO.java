package pl.sda.jg.ldz26java.voting.app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VoteDAO {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("MySQL");

    public void loadDatabase() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        final Project firstProject = new Project("First", "First project description");
        final Project secondProject = new Project("Second", "Second project description");
        final Project thirdProject = new Project("Third", "Third project description");
        final Project fourthProject = new Project("Fourth", "Fourth project description");
        final Project fifthProject = new Project("Fifth", "Fifth project description");

        final List<Project> projectsList = new ArrayList<>();
        projectsList.add(firstProject);
        projectsList.add(secondProject);
        projectsList.add(thirdProject);
        projectsList.add(fourthProject);
        projectsList.add(fifthProject);
        projectsList.sort(Comparator.comparing(Project::getName));

        final Voter firstVoter = new Voter();
        final Voter secondVoter = new Voter();
        final Voter thirdVoter = new Voter();
        final Voter fourthVoter = new Voter();
        final Voter fifthVoter = new Voter();

        entityManager.getTransaction().begin();
        for (Project project : projectsList) {
            entityManager.persist(project);
        }
        entityManager.persist(firstVoter);
        entityManager.persist(secondVoter);
        entityManager.persist(thirdVoter);
        entityManager.persist(fourthVoter);
        entityManager.persist(fifthVoter);
        entityManager.getTransaction().commit();

        entityManager.close();

    }

    public String voteForProject(Long projectId, Long voterId, String vote) {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        entityManager.getTransaction().begin();

        final boolean isProjectPresent = isSuchProjectPresent(projectId, entityManager);
        final boolean isVoterPresent = isSuchVoterPresent(voterId, entityManager);

        boolean voted = false;
        boolean voteOpen = isVoteOpen(projectId, entityManager);

        if (isProjectPresent && isVoterPresent) {
            voted = hasVoted(projectId, voterId, entityManager);
        }

        if(!isProjectPresent) {
            return "There's no such project on projects list!";
        } else if(!isVoterPresent) {
            return "Wrong voter id!";
        } else if (!voteOpen) {
            return "Voting for that project has been closed!";
        } else if (voted) {
            return "Already voted for that project";
        } else {
            Vote v = new Vote(projectId, voterId, vote);
            entityManager.persist(v);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return "Voted!";
    }

    private boolean isSuchProjectPresent(Long projectId, EntityManager entityManager) {
        final TypedQuery<Project> query = entityManager.createQuery(
                "SELECT p " +
                        "FROM Project p " +
                        "WHERE p.id = :projectId", Project.class
        );

        return query.setParameter("projectId", projectId)
                .getResultList()
                .stream()
                .findAny()
                .isPresent();
    }

    private boolean isSuchVoterPresent(Long voterId, EntityManager entityManager) {
        final TypedQuery<Voter> query = entityManager.createQuery(
                "SELECT v " +
                        "FROM Voter v " +
                        "WHERE v.id = :voterId", Voter.class
        );

        return query.setParameter("voterId", voterId)
                .getResultList()
                .stream()
                .findAny()
                .isPresent();
    }

    private boolean isVoteOpen(Long projectId, EntityManager entityManager) {
        TypedQuery<Project> query = entityManager.createQuery(
                "SELECT p " +
                        "FROM Project p " +
                        "WHERE p.id = :projectId", Project.class);

        return query.setParameter("projectId", projectId)
                .getSingleResult().isVoteOpen();
    }

    private boolean hasVoted(Long projectId, Long voterId, EntityManager entityManager) {
        TypedQuery<Vote> query = entityManager.createQuery(
                "SELECT v " +
                        "FROM Vote v " +
                        "WHERE v.voterId = :voterId AND v.projectId = :projectId", Vote.class);

        return query.setParameter("voterId", voterId)
                .setParameter("projectId", projectId)
                .getResultList()
                .stream()
                .findAny().isPresent();
    }

    public String closeVoting(Long projectId) {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        entityManager.getTransaction().begin();
        final Project project = entityManager.find(Project.class, projectId);
        if(!project.isVoteOpen()) {
            return "Project has been closed sometime ago";
        } else {
            project.setVoteOpen(false);
            entityManager.getTransaction().commit();
            entityManager.close();
            return "Voting closed for project: " + project.getName();
        }
    }

    public ProjectDetails projectDetails(Long projectId) {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        return entityManager.createQuery(
                "SELECT new pl.sda.jg.ldz26java.voting.app.ProjectDetails(p.name, p.description, p.voteOpen, SUM(v.voteFor), SUM(v.voteAgainst)) " +
                        "FROM Vote v " +
                        "JOIN Project p " +
                        "ON p.id = v.projectId " +
                        "WHERE v.projectId = :projectId", ProjectDetails.class).setParameter("projectId", projectId).getSingleResult();
    }
}
