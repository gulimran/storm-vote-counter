package imran.service;

import imran.domain.Candidate;
import imran.domain.Vote;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CounterService implements Serializable {

    private Map<Candidate, Integer> voteCount = new ConcurrentHashMap<>();

    public Map<Candidate, Integer> updateVoteCount(Vote vote) {
        Integer count = voteCount.get(vote.getCandidate());
        if (count == null) count = 0;
        voteCount.put(vote.getCandidate(), ++count);
        return voteCount;
    }

}
