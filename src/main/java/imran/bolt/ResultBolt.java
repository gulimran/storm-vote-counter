package imran.bolt;

import imran.domain.Candidate;
import imran.service.ResultService;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

@Slf4j
public class ResultBolt extends BaseBasicBolt {

    private ResultService resultService;

    public ResultBolt(ResultService resultService) {
        this.resultService = resultService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        Map<Candidate, Integer> voteCount = (Map<Candidate, Integer>) tuple.getValueByField("voteCount");
        log.debug("Results so far are: {}", voteCount);
        resultService.updateVoteCount(voteCount);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }
}
