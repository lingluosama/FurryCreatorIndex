package org.rookie.data.service.impl;

import lombok.RequiredArgsConstructor;
import org.rookie.data.service.IRoleService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupService implements ApplicationRunner {
    
    private final IRoleService roleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        roleService.setRolePermissionToRedis();
    }
}
