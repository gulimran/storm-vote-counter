package imran.service;

import imran.api.VoteService;
import imran.api.VoterService;
import imran.domain.Candidate;
import imran.domain.Vote;
import imran.domain.Voter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryVoteService implements VoteService {

    private List<Candidate> candidates = new InMemoryCandidateService().allCandidates();
    private VoterService voterService = new InMemoryVoterService();

    private Integer count = 0;

    @Override
    public Vote nextVote() {
        Voter voter = voterService.nextVoter();

        if (count >= candidates.size()) {
            count = 0;
        }

        Candidate candidate = candidates.get(count++);
        return Vote.builder().voter(voter).candidate(candidate).build();
    }
}
