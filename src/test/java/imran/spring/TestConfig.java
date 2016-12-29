package imran.spring;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = {
        "imran.test"})
@Import({SpringConfig.class})
public class TestConfig {
}
