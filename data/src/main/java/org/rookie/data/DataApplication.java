package org.rookie.data;

import com.mybatisflex.core.datasource.DataSourceManager;
import org.mybatis.spring.annotation.MapperScan;
import org.rookie.data.config.DataSourceStrategy;import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("org.rookie.data.mapper")
@EnableScheduling
public class DataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }

}
