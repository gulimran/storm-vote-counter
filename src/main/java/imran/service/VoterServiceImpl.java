package imran.service;

import imran.api.VoterService;
import imran.domain.Voter;

import java.util.ArrayList;
import java.util.List;

public class VoterServiceImpl implements VoterService {

    @Override
    public Voter nextVoter() {
        return getNextRandomVoter();
    }

    private static Integer voterCount = 0;

    private synchronized static Voter getNextRandomVoter() {
        return voters.get(voterCount++);
    }

    private static List<Voter> voters = new ArrayList<Voter>() {{
        for (int i=0; i<10000; i++) {
            add(Voter.builder().id(Integer.toUnsignedLong(i)).name("voter"+i).build());
        }
    }};
}
