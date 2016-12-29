package imran.bolt;

import imran.domain.Result;
import imran.domain.Vote;
import imran.service.CounterService;
import imran.service.ServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

@Slf4j
public class CounterBolt extends BaseBasicBolt {

    private final ServiceProvider serviceProvider;

    public CounterBolt(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        Vote vote = (Vote) tuple.getValueByField("vote");
        Result result = serviceProvider.getBean(CounterService.class).addVote(vote);
        log.debug("Vote count: {}", result);
        collector.emit(new Values(result));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("voteCount"));
    }

}
