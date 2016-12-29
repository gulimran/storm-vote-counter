package imran.service;

import imran.domain.Candidate;
import imran.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ResultService {

    private static final Map<Candidate, Integer> VOTING_RESULTS = new ConcurrentHashMap<>();

    @Autowired
    private RankingService rankingService;

    public synchronized void updateVoteCount(Result result) {
        Integer interimResult = VOTING_RESULTS.getOrDefault(result.getCandidate(), 0);
        VOTING_RESULTS.put(result.getCandidate(), interimResult+result.getVoteCount());
    }

    public List<Result> getVotingResults() {
        List<Result> results = VOTING_RESULTS.entrySet().stream()
                .map(entry -> new Result(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        rankingService.rank(results);
        return results;
    }

}
