package imran.topology;

import imran.bolt.PrinterBolt;
import imran.spout.VoteSpout;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

@Slf4j
@AllArgsConstructor
public class VoteCountTopology {

    private final String topologyName;
    private final Config configuration;

    public StormTopology create() {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("spout", new VoteSpout(), 5);
        topologyBuilder.setBolt("print", new PrinterBolt(), 2).shuffleGrouping("spout");

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
        }
    }
}
