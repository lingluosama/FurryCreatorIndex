package org.rookie.business.config;

import feign.codec.ErrorDecoder;
import org.rookie.business.exception.BusinessFeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    
    @Bean
    public ErrorDecoder errorDecoder() {
        return new BusinessFeignErrorDecoder();
    }
}
