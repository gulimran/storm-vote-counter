package imran.spring;

import imran.bolt.CounterBolt;
import imran.bolt.ResultBolt;
import imran.service.ServiceProvider;
import imran.spout.VoteSpout;
import imran.topology.VoteCountTopology;
import org.apache.storm.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StormConfig {

    private static ServiceProvider serviceProvider;

    @Bean
    public ServiceProvider serviceProvider() {
        if (serviceProvider == null) {
            serviceProvider = new ServiceProvider();
        }
        return serviceProvider;
    }

    @Bean
    public Config config() {
        Config conf = new Config();
        conf.setDebug(true);
        return conf;
    }

    @Bean
    public VoteCountTopology voteCountTopology() {
        return new VoteCountTopology("vote-count", config(), serviceProvider());
    }

    @Bean
    public VoteSpout voteSpout() {
        return new VoteSpout(serviceProvider());
    }

    @Bean
    public CounterBolt counterBolt() {
        return new CounterBolt(serviceProvider());
    }

    @Bean
    public ResultBolt resultBolt() {
        return new ResultBolt(serviceProvider());
    }
}
