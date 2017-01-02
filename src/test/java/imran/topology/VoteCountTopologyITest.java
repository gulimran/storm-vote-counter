package imran.topology;

import imran.spout.NotifyingVoteSpout;
import imran.storm.StormIntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class VoteCountTopologyITest extends StormIntegrationTest {


    private static final String SPOUT_NAME_TO_OVERRIDE = "Vote-Spout";
    private static final Class OVERRIDING_SPOUT_CLASS = NotifyingVoteSpout.class;

    private long startTime;

    @Override
    protected String getTopologyName() {
        return "vote-count-topology";
    }

    @Override
    protected String getTopologyFilename() {
        return getTopologyName() + ".yaml";
    }

    @Before
    public void setup() throws Exception  {
        startTime = System.currentTimeMillis();
    }

    @After
    public void tearDown(){
        killTopology();
        log.info("Total test Time " + (System.currentTimeMillis() - startTime) / 1000 + " seconds");
    }

    @Test
    public void test() throws Exception {
        startTopology(SPOUT_NAME_TO_OVERRIDE, OVERRIDING_SPOUT_CLASS, 30);
    }
}