package imran;

import imran.topology.VoteCountTopology;
import org.apache.storm.generated.StormTopology;
import org.springframework.beans.factory.annotation.Autowired;

public class Main {

    @Autowired
    private VoteCountTopology voteCountTopology;

    public void run() {
        StormTopology topology = voteCountTopology.create();
        voteCountTopology.deploy(topology, 30000);
    }
}
