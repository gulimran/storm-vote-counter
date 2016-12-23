package imran.service;

import imran.api.VoteService;
import imran.api.VoterService;
import imran.domain.Candidate;
import imran.domain.Vote;
import imran.domain.Voter;

import java.util.List;
import java.util.Random;

public class VoteServiceImpl implements VoteService {

    private List<Candidate> candidates = new CandidateServiceImpl().allCandidates();
    private VoterService voterService = new VoterServiceImpl();
    private Random randomGenerator = new Random();

    @Override
    public Vote castVote() {
        Voter voter = voterService.nextVoter();
        Candidate candidate = candidates.get(randomGenerator.nextInt(candidates.size()));
        return Vote.builder().voter(voter).candidate(candidate).build();
    }
}
