package imran;

import imran.spring.SpringContext;
import imran.spring.TestConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class MainTest {

    @Autowired
    protected ApplicationContext context;

    @Autowired
    private Main main;

    @Before
    public void setup() throws Exception {
        setSpringContext();
    }

    @Test
    public void test() throws Exception {
        main.run();
    }

    private void setSpringContext() throws Exception {
        Field field = SpringContext.class.getDeclaredFields()[0];
        field.setAccessible(true);
        field.set(null, context);
    }

}