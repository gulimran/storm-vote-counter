package imran.spring;

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
}
