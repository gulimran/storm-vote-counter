package imran.spring;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public abstract class SpringIntegrationTest {

    @Autowired
    protected ApplicationContext context;

    @Before
    public void setup() throws Exception {
        setSpringContext();
    }

    private void setSpringContext() throws Exception {
        Field field = SpringContext.class.getDeclaredFields()[0];
        field.setAccessible(true);
        field.set(null, context);
    }
}
