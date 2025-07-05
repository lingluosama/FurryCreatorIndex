package org.rookie.admin;

import com.mybatisflex.core.datasource.DataSourceManager;import org.rookie.admin.config.DataSourceStrategy;import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        DataSourceManager.setDataSourceShardingStrategy(new DataSourceStrategy());
    }

}
