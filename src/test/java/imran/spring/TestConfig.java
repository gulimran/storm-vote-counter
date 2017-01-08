package imran.spring;

import imran.api.VoteService;
import imran.domain.Vote;
import imran.storm.EmittedMessageProvider;
import imran.test.InMemoryVoteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@Import({SpringConfig.class})
@ComponentScan("imran.spring, imran.test")
public class TestConfig {

    @Bean
    public VoteService voteService() {
        return new InMemoryVoteService();
    }

    @Bean
    public EmittedMessageProvider<Vote> voteEmittedMessageProvider() {
        return new EmittedMessageProvider<>();
    }
}
