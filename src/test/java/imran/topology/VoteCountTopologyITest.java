package imran.topology;

import imran.domain.Result;
import imran.service.ResultService;
import imran.spout.NotifyingVoteSpout;
import imran.spring.ServiceProvider;
import imran.storm.StormIntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.utils.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class VoteCountTopologyITest extends StormIntegrationTest {


    private static final String SPOUT_NAME_TO_OVERRIDE = "Vote-Spout";
    private static final Class OVERRIDING_SPOUT_CLASS = NotifyingVoteSpout.class;

    private long startTime;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ServiceProvider serviceProvider;

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
        Utils.sleep(10000);

        List<Result> votingResults = serviceProvider.getBean(ResultService.class).getVotingResults();
        assertThat(votingResults.get(0).getCandidate().getId(), is(1L));
        assertThat(votingResults.get(0).getVoteCount() > 0, is(true));
    }
}