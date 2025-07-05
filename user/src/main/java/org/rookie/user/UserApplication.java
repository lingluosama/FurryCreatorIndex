package org.rookie.user;

import com.mybatisflex.core.datasource.DataSourceManager;
import org.mybatis.spring.annotation.MapperScan;
import org.rookie.config.RWSeparationStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.rookie.config.RandomReadDataSourceStrategy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("org.rookie.user.mapper")
@EnableScheduling
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        DataSourceManager.setDataSourceShardingStrategy(new RWSeparationStrategy());
    }

}
