package org.rookie.data.service.impl;

import com.mybatisflex.core.datasource.DataSourceManager;
import com.mybatisflex.core.keygen.KeyGeneratorFactory;
import lombok.RequiredArgsConstructor;
import org.rookie.data.config.DataSourceStrategy;
import org.rookie.data.service.IRoleService;
import org.rookie.data.utils.CustomIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;

@Component
@RequiredArgsConstructor
public class StartupService implements ApplicationRunner {
    
    private final IRoleService roleService;

    @Value("${fc-config.node}")
    long workerId;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //设置角色权限缓存
        roleService.setRolePermissionToRedis();
        //设置数据源分片策略
        DataSourceManager.setDataSourceShardingStrategy(new DataSourceStrategy());

    }
}
