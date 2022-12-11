package hello.core.scope;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeBeanTest {

  @Test
  void prototypeBeanFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
        ProtoTypeBean.class);
    System.out.println("find prototypeBean1");
    ProtoTypeBean protoTypeBean1 = ac.getBean(ProtoTypeBean.class);
    System.out.println("find prototypeBean2");
    ProtoTypeBean protoTypeBean2 = ac.getBean(ProtoTypeBean.class);
    System.out.println("protoTypeBean1 = " + protoTypeBean1);
    System.out.println("protoTypeBean2 = " + protoTypeBean2);
    assertThat(protoTypeBean1).isNotSameAs(protoTypeBean2);
    ac.close();
  }

  @Test
  void singletonClientUsePrototype() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class,
        ProtoTypeBean.class);
    ClientBean clientBean1 = ac.getBean(ClientBean.class);
    int count1 = clientBean1.logic();
    assertThat(count1).isEqualTo(1);
    ClientBean clientBean2 = ac.getBean(ClientBean.class);
    int count2 = clientBean2.logic();
    assertThat(count2).isEqualTo(1);
  }

  @Scope("singleton")
  static class ClientBean {

/*    @Autowired
    private ObjectProvider<ProtoTypeBean> prototypeBeanProvider;*/

    @Autowired
    private Provider<ProtoTypeBean> prototypeBeanProvider;


    public int logic() {
      ProtoTypeBean protoTypeBean = prototypeBeanProvider.get();
      protoTypeBean.addCount();
      return protoTypeBean.getCount();
    }
  }

  @Scope("prototype")
  static class ProtoTypeBean {

    private int count;

    @PostConstruct
    public void init() {
      System.out.println("PrototypeBean.init");
    }

    @PreDestroy
    public void destroy() {
      System.out.println("PrototypeBean.destroy");
    }

    public int getCount() {
      return count;
    }

    public void setCount(int count) {
      this.count = count;
    }

    public void addCount() {
      count++;
    }
  }
}
