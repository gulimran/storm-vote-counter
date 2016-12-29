package imran.spring;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContext {
    private static volatile ConfigurableApplicationContext context;

    public static void initContext() {
        if (context == null) {
            synchronized (SpringContext.class) {
                if (context == null) {
                    System.out.println("Creating Spring context");
                    context = new AnnotationConfigApplicationContext(SpringConfig.class);
                }
            }
        }
    }

    public static ConfigurableApplicationContext getContext() {
        initContext();
        return context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return getContext().getBean(clazz);
    }
}
