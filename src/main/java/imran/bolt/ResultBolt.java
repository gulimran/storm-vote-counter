package imran.bolt;

import imran.domain.Result;
import imran.service.ResultService;
import imran.service.ServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

@Slf4j
public class ResultBolt extends BaseBasicBolt {

    private final ServiceProvider serviceProvider;

    public ResultBolt(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        Result result = (Result) tuple.getValueByField("voteCount");
        log.debug("Result: {}", result);
        serviceProvider.getBean(ResultService.class).updateVoteCount(result);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }
}
