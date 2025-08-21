package org.rookie.data.config;

import com.mybatisflex.core.keygen.KeyGeneratorFactory;
import jakarta.annotation.PostConstruct;
import org.rookie.data.utils.CustomIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfig {

    @Value("${fc-config.node}")
    long workerId;

    
    //在启动前注册自定义的ID生成器
    @PostConstruct
    public void registerCustomIdGenerator() {
        KeyGeneratorFactory.register("FcIdGenerator", new CustomIdGenerator(workerId));
    }
}