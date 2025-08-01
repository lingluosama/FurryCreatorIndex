package org.rookie.business;

import com.mybatisflex.core.datasource.DataSourceManager;
import org.mybatis.spring.annotation.MapperScan;
import org.rookie.config.RWSeparationStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class BusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
        DataSourceManager.setDataSourceShardingStrategy(new RWSeparationStrategy());
    }

}
