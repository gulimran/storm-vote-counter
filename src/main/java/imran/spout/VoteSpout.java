package imran.spout;

import imran.api.VoteService;
import imran.domain.Vote;
import imran.service.ServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;

@Slf4j
public class VoteSpout extends BaseRichSpout {

    private final ServiceProvider serviceProvider;
    private SpoutOutputCollector collector;

    public VoteSpout(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        Utils.sleep(100);

        Vote vote = serviceProvider.getBean(VoteService.class).castVote();
        log.debug("Emitting vote: {}", vote);
        collector.emit(new Values(vote));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("vote"));
    }
}
