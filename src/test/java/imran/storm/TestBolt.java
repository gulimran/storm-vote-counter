package imran.storm;

import imran.spring.ServiceProvider;
import lombok.Setter;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.List;
import java.util.Map;

import static org.springframework.util.CollectionUtils.isEmpty;

@Setter
public class TestBolt extends BaseRichBolt {

    public enum Behaviour {
        PASS,
        FAIL
    }

    private ServiceProvider serviceProvider;
    private String inputField;
    private OutputCollector outputCollector;
    private List<Behaviour> behaviours;

    private Integer executionCount = 0;
    private boolean reply = false;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.outputCollector = collector;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // no-op
    }

    @SuppressWarnings("unchecked")
    public void execute(Tuple tuple) {
        if (isEmpty(behaviours) || Behaviour.PASS == behaviours.get(executionCount)) {
            Object value = tuple.getValueByField(inputField);
            serviceProvider.getBean(EmittedMessageProvider.class).addMessage(value);
            if (reply) outputCollector.ack(tuple);
        } else if (Behaviour.FAIL == behaviours.get(executionCount)) {
            if (reply) outputCollector.fail(tuple);
        }
        executionCount++;
    }
}
