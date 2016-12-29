package imran.spring;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PropertySourcesHelper {

    public static final String DEFAULT_YAML_RESOURCE = "spring/local.yaml";
    public static final String DEFAULT_PROPERTIES_RESOURCE = "spring.properties";

    public static PropertySourcesPlaceholderConfigurer yamlPlaceholderConfigurer() {
        return yamlPlaceholderConfigurer(DEFAULT_YAML_RESOURCE);
    }

    public static PropertySourcesPlaceholderConfigurer yamlPlaceholderConfigurer(String defaultResource) {
        Resource[] resources = getResources(defaultResource);
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(resources);

        return getConfigurer(yaml.getObject());
    }

    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return propertyPlaceholderConfigurer(DEFAULT_PROPERTIES_RESOURCE);
    }

    @SneakyThrows(IOException.class)
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer(String defaultResource) {
        Resource[] resources = getResources(defaultResource);
        PropertiesFactoryBean properties = new PropertiesFactoryBean();
        properties.setLocations(resources);

        return getConfigurer(properties.getObject());
    }

    private static Resource[] getResources(String defaultResource) {
        String path = System.getProperty("app.config");

        Resource[] resources;
        if (path != null) {
            resources = new Resource[]{new FileSystemResource(path)};
        } else {
            log.warn("Property 'config.path' property not provided, using default classpath resource " + defaultResource);
            resources = new Resource[]{new ClassPathResource(defaultResource)};
        }
        return resources;
    }

    private static PropertySourcesPlaceholderConfigurer getConfigurer(Properties object) {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        pspc.setProperties(object);
        pspc.setIgnoreUnresolvablePlaceholders(false);
        pspc.setIgnoreResourceNotFound(true);
        return pspc;
    }
}
