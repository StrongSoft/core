package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetWorkClient {

  private String url;

  public NetWorkClient() {
    System.out.println("생성자 호출, url = " + url);
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void connect() {
    System.out.println("connect = " + url);
  }

  public void call(String message) {
    System.out.println("call = " + url + " message = " + message);
  }

  public void disconnect() {

  }

  @PostConstruct
  public void init() {
    connect();
    call("초기화 연결 메세지");
  }

  @PreDestroy
  public void close() {
    disconnect();
  }
}
