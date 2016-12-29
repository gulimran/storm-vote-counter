package imran.bolt;

import imran.domain.Candidate;
import imran.service.ResultService;
import imran.service.ServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

@Slf4j
public class ResultBolt extends BaseBasicBolt {

    private final ServiceProvider serviceProvider;

    public ResultBolt(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        Map<Candidate, Integer> voteCount = (Map<Candidate, Integer>) tuple.getValueByField("voteCount");
        log.debug("Results so far are: {}", voteCount);
        serviceProvider.getBean(ResultService.class).updateVoteCount(voteCount);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }
}
