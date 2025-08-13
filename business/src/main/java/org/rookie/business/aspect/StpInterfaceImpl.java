package org.rookie.business.aspect;


import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.rookie.consts.RedisKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private static final Logger log = LoggerFactory.getLogger(StpInterfaceImpl.class);
    private final RedisTemplate<String, Object> redisTemplate;
    
    @Override
    @SuppressWarnings("unchecked")
    public List<String> getPermissionList(Object o, String s) {
        
        List<String> roleList = (List<String>) StpUtil.getSession().get("roleList");
        if (roleList == null || roleList.isEmpty()) {
            return Collections.emptyList();
        }

        List<Set<String>> permissions = roleList.stream().map(role -> {
            String redisKey = RedisKeys.ROLE_PERMISSION_KEY_PREFIX + role;
            return (Set<String>) (Set<?>) redisTemplate.opsForSet().members(redisKey);
        }).toList();

        log.info("当前用户权限列表{}",roleList);
        
        return permissions.stream().flatMap(Set::stream).distinct().toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getRoleList(Object o, String s) {
        // 直接从当前会话中获取角色列表
        List<String> roleList = (List<String>) StpUtil.getSession().get("roleList");
        log.info("当前用户角色列表{}",roleList);
        if (roleList == null) {
            return Collections.emptyList();
        }
        return roleList;
    }
}
