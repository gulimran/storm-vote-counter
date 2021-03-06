package imran.bolt;

import imran.domain.Candidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

@Slf4j
public class PrinterBolt extends BaseBasicBolt {

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
//        Vote vote = (Vote) tuple.getValueByField("vote");
//        log.info("Received vote: {}", vote);
        Map<Candidate, Integer> voteCount = (Map<Candidate, Integer>) tuple.getValueByField("voteCount");
        log.info("Received voting results: {}", voteCount);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
