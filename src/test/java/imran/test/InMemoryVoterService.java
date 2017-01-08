package imran.test;

import imran.api.VoterService;
import imran.domain.Voter;

import java.util.ArrayList;
import java.util.List;

public class InMemoryVoterService implements VoterService {

    private static final Integer NUMBER_OF_VOTERS = 10000;

    @Override
    public Voter nextVoter() {
        return getNextRandomVoter();
    }

    private static Integer voterCount = 0;

    private synchronized static Voter getNextRandomVoter() {
        if (voterCount < NUMBER_OF_VOTERS) {
             return voters.get(voterCount++);
        }
        voterCount++;
        return null;
    }

    private static List<Voter> voters = new ArrayList<Voter>() {{
        for (int i=0; i<NUMBER_OF_VOTERS; i++) {
            add(Voter.builder().id(Integer.toUnsignedLong(i)).name("voter"+i).build());
        }
    }};
}
