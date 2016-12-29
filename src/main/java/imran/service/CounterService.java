package imran.service;

import imran.domain.Result;
import imran.domain.Vote;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

    public Result addVote(Vote vote) {
        return Result.builder().candidate(vote.getCandidate()).voteCount(1).build();
    }

}
