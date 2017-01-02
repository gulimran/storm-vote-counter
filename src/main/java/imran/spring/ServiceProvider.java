package imran.spring;

import java.io.Serializable;

public class ServiceProvider implements Serializable {
    public <T> T getBean(Class<T> clazz) {
        return SpringContext.getBean(clazz);
    }
}
