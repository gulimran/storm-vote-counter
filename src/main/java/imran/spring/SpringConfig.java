package imran.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.StandardEnvironment;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = {
        "imran.service",
        "imran.topology"})
@Import({StormConfig.class})
@Slf4j
public class SpringConfig {

    @Bean
    public static StandardEnvironment standardEnvironment() {
        return new StandardEnvironment();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer ppc() throws IOException {
        String environment = getProperty("environment");
        log.info("Environment : "+ environment);

        String defaultResource = "spring/" + environment + ".yaml";
        return PropertySourcesHelper.yamlPlaceholderConfigurer(defaultResource);
    }

    private static String getProperty(String propertyName){
        String propertyValue = standardEnvironment().getProperty(propertyName);
        if(propertyValue == null){
            log.warn("Property {} not found in standardEnviornment, Looking into System.properties", propertyName);
            propertyValue = System.getProperty(propertyName, "local");
            log.info("Property Name : {} Value : ", propertyName, propertyValue);
        }
        return propertyValue;
    }

}
