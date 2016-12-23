package imran;

import imran.topology.VoteCountTopology;
import org.apache.storm.Config;
import org.apache.storm.generated.StormTopology;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() {
        Config conf = new Config();
        conf.setDebug(true);
        VoteCountTopology voteCountTopology = new VoteCountTopology("vote-count", conf);
        StormTopology topology = voteCountTopology.create();
        voteCountTopology.deploy(topology, 10000);
    }
}
