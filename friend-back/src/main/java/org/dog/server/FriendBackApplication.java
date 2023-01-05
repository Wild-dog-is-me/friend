package org.dog.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.dog.server.mapper")
public class FriendBackApplication {

  public static void main(String[] args) {
    SpringApplication.run(FriendBackApplication.class, args);
  }

}
