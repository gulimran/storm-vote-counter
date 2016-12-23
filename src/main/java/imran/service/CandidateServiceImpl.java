package imran.service;

import imran.api.CandidateService;
import imran.domain.Candidate;

import java.util.ArrayList;
import java.util.List;

public class CandidateServiceImpl implements CandidateService {


    @Override
    public List<Candidate> allCandidates() {
        return candidates;
    }

    private static List<Candidate> candidates = new ArrayList<Candidate>() {{
        for (int i=0; i<5; i++) {
            add(Candidate.builder().id(Integer.toUnsignedLong(i)).name("candidate"+i).build());
        }
    }};
}
