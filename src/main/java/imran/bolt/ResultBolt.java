package imran.bolt;

import imran.domain.Result;
import imran.service.ResultService;
import imran.spring.ServiceProvider;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

@Setter
@Slf4j
public class ResultBolt extends BaseBasicBolt {

    private ServiceProvider serviceProvider;
    private String inputField;

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        Result result = (Result) tuple.getValueByField(inputField);
        log.debug("Result: {}", result);
        serviceProvider.getBean(ResultService.class).updateVoteCount(result);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }
}
