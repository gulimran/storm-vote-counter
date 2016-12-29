package imran.test;

import imran.api.CandidateService;
import imran.domain.Candidate;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCandidateService implements CandidateService {

    private static final Integer NUMBER_OF_CANDIDATES = 5;

    @Override
    public List<Candidate> allCandidates() {
        return candidates;
    }

    private static List<Candidate> candidates = new ArrayList<Candidate>() {{
        for (int i=0; i<NUMBER_OF_CANDIDATES; i++) {
            add(Candidate.builder().id(Integer.toUnsignedLong(i)).name("candidate"+i).build());
        }
    }};
}
