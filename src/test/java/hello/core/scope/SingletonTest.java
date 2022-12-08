package hello.core.scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonTest {

  @Test
  void singletonBeanFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
        SingletonTest.class);
    SingletonTest singletonTest1 = ac.getBean(SingletonTest.class);
    SingletonTest singletonTest2 = ac.getBean(SingletonTest.class);

    System.out.println("singletonTest1 = " + singletonTest1);
    System.out.println("singletonTest2 = " + singletonTest2);
    Assertions.assertThat(singletonTest1).isSameAs(singletonTest2);

    ac.close();
  }

  @Scope("singleton")
  static class SingleTonBean {

    @PostConstruct
    public void init() {
      System.out.println("SingletonBean.init");
    }

    @PreDestroy
    public void destroy() {
      System.out.println("SingletonBean.destroy");
    }
  }
}
