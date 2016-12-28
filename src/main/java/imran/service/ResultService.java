package imran.service;

import imran.domain.Candidate;
import imran.domain.Result;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ResultService implements Serializable {

    private static final Map<Candidate, Integer> VOTING_RESULTS = new ConcurrentHashMap<>();
    private RankingService rankingService;

    public ResultService() {
        this.rankingService = new RankingService();
    }

    public synchronized void updateVoteCount(Map<Candidate, Integer> result) {
        result.entrySet().forEach(entry -> {
            Integer count = VOTING_RESULTS.get(entry.getKey());
            if (count == null) count = 0;
            VOTING_RESULTS.put(entry.getKey(), count+entry.getValue());
        });
    }

    public List<Result> getVotingResults() {
        List<Result> results = VOTING_RESULTS.entrySet().stream()
                .map(entry -> new Result(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        rankingService.rank(results);
        return results;
    }

}
