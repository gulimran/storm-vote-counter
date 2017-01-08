package imran.storm;

import imran.spring.SpringIntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.flux.FluxBuilder;
import org.apache.storm.flux.model.BoltDef;
import org.apache.storm.flux.model.ExecutionContext;
import org.apache.storm.flux.model.SpoutDef;
import org.apache.storm.flux.model.TopologyDef;
import org.apache.storm.flux.parser.FluxParser;
import org.apache.storm.generated.KillOptions;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.generated.TopologySummary;
import org.apache.storm.testing.CompleteTopologyParam;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Slf4j
public abstract class StormIntegrationTest extends SpringIntegrationTest {

    private static LocalCluster cluster;
    private long startTime;
    private static StormTracker stormTracker = StormTracker.getInstance();

    @BeforeClass
    public static void startCluster(){
        cluster = new LocalCluster();
    }

    @AfterClass
    public static void stopCluster(){
        cluster.shutdown();
    }

    @Before
    public void trackExecutionTime() throws Exception  {
        startTime = System.currentTimeMillis();
    }

    @After
    public void logExecutionTime(){
        killTopology();
        log.info("Total test Time " + (System.currentTimeMillis() - startTime) / 1000 + " seconds");
    }

    protected abstract String getTopologyName();

    protected StormTopology startTopology(String overrideComponentId, Class overrideClass, long timeout)
            throws Exception {
        log.info("Topology {} being Created and submitted", getTopologyName());

        String id = getTopologyName();
        stormTracker.startTracking(id);
        Resource resource = new ClassPathResource(getTopologyName() + ".yaml");
        TopologyDef topologyDef = FluxParser.parseFile(resource.getFile().getAbsolutePath(), false, false, null, false);

        Config config = FluxBuilder.buildConfig(topologyDef);

        CompleteTopologyParam completeTopologyParam = new CompleteTopologyParam();
        completeTopologyParam.setStormConf(config);
        completeTopologyParam.setCleanupState(true);

        ExecutionContext executionContext = new ExecutionContext(FluxParser.parseFile(resource.getFile().getAbsolutePath(), false, false, null, false), config);

        if (!isBlank(overrideComponentId)) {
            overrideTopology(executionContext, overrideComponentId, overrideClass);
        }

        StormTopology topology = FluxBuilder.buildTopology(executionContext);

        cluster.submitTopology(getTopologyName(), completeTopologyParam.getStormConf(), topology);
        boolean topologyStartTimeout = stormTracker.waitForSignal(id, timeout, SECONDS);
        assertThat(topologyStartTimeout, is(true));

        log.info("Topology {} Started" , getTopologyName());
        return topology;
    }

    protected void killTopology() {
        log.info("Topology {} being removed", getTopologyName());

        KillOptions killOptions = new KillOptions();
        killOptions.set_wait_secs(0);
        cluster.killTopologyWithOpts(getTopologyName(), killOptions);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.info("killTopology InterruptedException", e);
        }

        List<TopologySummary> topologySummaries = cluster.getClusterInfo().get_topologies();
        for(TopologySummary oneTopologySummary : topologySummaries){
            log.info(oneTopologySummary.get_name()+" : "+ oneTopologySummary.get_status());
        }
    }

    private void overrideTopology(ExecutionContext executionContext, String id, Class overrideClass) {
        if (id.endsWith("Spout")) {
            SpoutDef spoutDef = executionContext.getTopologyDef().getSpoutDef(id);
            spoutDef.setClassName(overrideClass.getName());
        } else if (id.endsWith("Bolt")) {
            BoltDef boltDef = executionContext.getTopologyDef().getBoltDef(id);
            boltDef.setClassName(overrideClass.getName());
        }
    }
}
