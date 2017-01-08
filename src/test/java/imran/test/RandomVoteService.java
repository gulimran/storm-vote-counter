package imran.test;

import imran.api.VoteService;
import imran.api.VoterService;
import imran.domain.Candidate;
import imran.domain.Vote;
import imran.domain.Voter;

import java.util.List;
import java.util.Random;

public class RandomVoteService implements VoteService {

    private List<Candidate> candidates = new InMemoryCandidateService().allCandidates();
    private VoterService voterService = new InMemoryVoterService();
    private Random randomGenerator = new Random();

    @Override
    public Vote nextVote() {
        Voter voter = voterService.nextVoter();
        Candidate candidate = candidates.get(randomGenerator.nextInt(candidates.size()));
        return Vote.builder().voter(voter).candidate(candidate).build();
    }
}
