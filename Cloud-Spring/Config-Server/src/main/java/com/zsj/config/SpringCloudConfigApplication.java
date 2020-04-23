package com.zsj.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ConfigurableApplicationContext;

@EnableConfigServer
@SpringBootApplication
public class SpringCloudConfigApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringCloudConfigApplication.class, args);

      /*
        // 测试用数据，仅用于Mysql 测试使用  http://localhost:8000/config/stage/
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        jdbcTemplate.execute("delete from properties");
        jdbcTemplate.execute("INSERT INTO properties VALUES(1, 'com.zsj.mes', 'test', 'config', 'stage', 'master')");
        jdbcTemplate.execute("INSERT INTO properties VALUES(2, 'com.zsj.mes', 'test', 'config', 'online', 'master')");
        */
    }

}
