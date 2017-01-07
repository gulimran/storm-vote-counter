package imran.spout;

import imran.storm.StormTracker;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;

import java.util.Map;

public class NotifyingVoteSpout extends VoteSpout {

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        super.open(conf, context, collector);
        StormTracker.getInstance().sendProcessedSignal((String) conf.get("topology.name"));
    }

    @Override
    public void close() {
        super.close();
        StormTracker.getInstance().sendProcessedSignal("2");
    }

    @Override
    public void ack(Object messageId) {
        super.ack(messageId);
        StormTracker.getInstance().sendProcessedSignal("2");
    }
    @Override
    public void fail(Object messageId) {
        super.fail(messageId);
        StormTracker.getInstance().sendProcessedSignal("2");
    }
}
