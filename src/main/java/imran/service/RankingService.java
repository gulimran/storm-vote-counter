package imran.service;

import imran.domain.Result;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class RankingService implements Serializable {

    public void rank(List<Result> results) {
        Collections.sort(results, (result1, result2) -> result2.getVoteCount() - result1.getVoteCount());
    }
}
