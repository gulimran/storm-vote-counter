package imran.spout;

import imran.domain.Vote;
import imran.storm.EmittedMessageProvider;
import imran.storm.StormIntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class VoteSpoutITest extends StormIntegrationTest {

    private static final String SPOUT_NAME_TO_OVERRIDE = "Vote-Spout";
    private static final Class OVERRIDING_SPOUT_CLASS = NotifyingVoteSpout.class;

    @Autowired
    private EmittedMessageProvider<Vote> messageProvider;

    @Before
    public void setup() {
        messageProvider.reset();
    }

    @Override
    protected String getTopologyName() {
        return "test-vote-spout-topology";
    }

    @Test
    public void testVoteCountSpout_EmitsCandidateAndVoterInExpectedOrder() throws Exception {
        Long expectedCandidateId = 0L;
        Long expectedVoterId = 0L;

        startTopology(SPOUT_NAME_TO_OVERRIDE, OVERRIDING_SPOUT_CLASS, 30);
        Utils.sleep(10000);

        List<Vote> results = messageProvider.getMessages();
        assertThat(results.get(0).getCandidate().getId(), is(expectedCandidateId));
        assertThat(results.get(0).getVoter().getId(), is(expectedVoterId));
    }
}