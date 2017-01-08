package imran.spout;

import imran.domain.Vote;
import imran.storm.StormIntegrationTest;
import imran.storm.TestBolt;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.utils.Utils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class VoteSpoutITest extends StormIntegrationTest {

    private static final String SPOUT_NAME_TO_OVERRIDE = "Vote-Spout";
    private static final Class OVERRIDING_SPOUT_CLASS = NotifyingVoteSpout.class;

    @Override
    protected String getTopologyName() {
        return "test-vote-spout-topology";
    }

    @Test
    public void testVoteCountTopology() throws Exception {
        startTopology(SPOUT_NAME_TO_OVERRIDE, OVERRIDING_SPOUT_CLASS, 30);
        Utils.sleep(10000);

        List<Object> results = TestBolt.getResults();
        Long expectedCandidateId = ((Vote) results.get(0)).getCandidate().getId();
        Long expectedVoterId = ((Vote) results.get(0)).getVoter().getId();
        assertThat(expectedCandidateId, is(0L));
        assertThat(expectedVoterId, is(0L));
    }
}