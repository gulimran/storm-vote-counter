package imran.topology;

import imran.bolt.CounterBolt;
import imran.bolt.ResultBolt;
import imran.domain.Result;
import imran.service.ResultService;
import imran.spout.VoteSpout;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

import java.util.List;

@Slf4j
public class VoteCountTopology {

    private final String topologyName;
    private final Config configuration;
    private ResultService resultService;

    public VoteCountTopology(String topologyName, Config configuration) {
        this.topologyName = topologyName;
        this.configuration = configuration;
    }

    public StormTopology create() {
        resultService = new ResultService();
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("spout", new VoteSpout(), 5);
        topologyBuilder.setBolt("count", new CounterBolt(), 3).shuffleGrouping("spout");
        topologyBuilder.setBolt("result", new ResultBolt(resultService), 1).fieldsGrouping("count",  new Fields("voteCount"));
//        topologyBuilder.setBolt("print", new PrinterBolt(), 1).shuffleGrouping("count");

        return topologyBuilder.createTopology();
    }

    public void deploy(StormTopology topology)
            throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        configuration.setNumWorkers(3);
        StormSubmitter.submitTopologyWithProgressBar(topologyName, configuration, topology);
    }

    public void deploy(StormTopology topology, Integer timeout) {
        LocalCluster cluster = new LocalCluster();

        try {
            cluster.submitTopology(topologyName, configuration, topology);
            Utils.sleep(timeout);
        } finally {
            cluster.killTopology(topologyName);
            cluster.shutdown();
            List<Result> votingResults = resultService.getVotingResults();
            log.info("###########################################################");
            log.info("Voting results: {}", votingResults);
            log.info("Winner is: {}", votingResults.get(0));
            log.info("###########################################################");
        }
    }
}
