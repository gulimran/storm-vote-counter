package imran.bolt;

import imran.domain.Vote;
import imran.service.CounterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

@Slf4j
public class CounterBolt extends BaseBasicBolt {

    private final CounterService counterService;

    public CounterBolt() {
        this.counterService = new CounterService();
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        Vote vote = (Vote) tuple.getValueByField("vote");
        log.debug("Received vote: {}", vote);
        collector.emit(new Values(counterService.updateVoteCount(vote)));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("voteCount"));
    }

}
