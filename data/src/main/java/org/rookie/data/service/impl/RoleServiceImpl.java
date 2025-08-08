package org.rookie.data.service.impl;

import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.rookie.consts.RedisKeys;
import org.rookie.data.mapper.PermissionMapper;
import org.rookie.data.mapper.RoleMapper;
import org.rookie.data.mapper.RolePermissionMapper;
import org.rookie.data.mapper.UserRoleMapper;
import org.rookie.data.service.IRoleService;
import org.rookie.model.bo.RolePermissionBO;
import org.rookie.model.dto.RolePermissionSearchDTO;
import org.rookie.model.entity.database.Permission;
import org.rookie.model.entity.database.Role;
import org.rookie.model.entity.database.RolePermission;
import org.rookie.model.entity.database.UserRole;
import org.rookie.model.entity.database.table.PermissionTableDef;
import org.rookie.model.entity.database.table.RolePermissionTableDef;
import org.rookie.model.entity.database.table.RoleTableDef;
import org.rookie.model.entity.database.table.UserRoleTableDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.rookie.model.entity.database.table.PermissionTableDef.PERMISSION;
import static org.rookie.model.entity.database.table.RolePermissionTableDef.ROLE_PERMISSION;
import static org.rookie.model.entity.database.table.RoleTableDef.ROLE;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final UserRoleMapper userRoleMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final PermissionMapper permissionMapper;
    private final RedisTemplate<String,Object> redisTemplate;
    
    
    @Override
    public List<String> getUserRoles(Long uid) {
        
        QueryWrapper userRoles=QueryWrapper.create()
                .select(UserRoleTableDef.USER_ROLE.ROLE_ID)
                .from(UserRoleTableDef.USER_ROLE)
                .where(UserRoleTableDef.USER_ROLE.USER_ID.eq(uid));
        
        return this.queryChain()
                .select(ROLE.NAME)
                .from(ROLE)
                .where(ROLE.ID.in(userRoles))
                .list().stream()
                .map(Role::getName)
                .toList();
        
    }

    @Override
    public RolePermissionSearchDTO searchRoleWithPermission(String keyword, Integer offset, Integer limit) {
        QueryChain<Role> query = queryChain();

        if (keyword != null) {
            // 构建子查询，找到符合权限名称的角色 ID
            QueryWrapper roleIdsByPermission = QueryWrapper.create()
                    .select(ROLE_PERMISSION.ROLE_ID)
                    .from(ROLE_PERMISSION)
                    .where(ROLE_PERMISSION.PERMISSION_ID.in(
                            // 再次嵌套子查询，找到符合权限描述的权限 ID
                            QueryWrapper.create()
                                    .select(PERMISSION.ID)
                                    .from(PERMISSION)
                                    .where(PERMISSION.DESCRIPTION.like(keyword))
                    ));
            
            // 主查询：实现同时匹配角色名称和权限名称
            query.where(ROLE.NAME.like(keyword))
                    .or(ROLE.ID.in(roleIdsByPermission));
        }

        // 如果keyword为null，则where条件不会被添加，直接查询所有角色
        long count = query.count();

        List<Role> roleList = query.offset(offset).limit(limit).list();

        // 将合并角色权限的逻辑提取到公共方法中
        List<RolePermissionBO> boList = buildRolePermissions(roleList);

        return new RolePermissionSearchDTO(count, boList);
    }
    
    private List<RolePermissionBO> buildRolePermissions(List<Role> roleList) {
        return roleList.stream().map(role -> {
            RolePermissionBO bo = new RolePermissionBO();
            bo.setRole(role);
            bo.setPermissions(getRolePermissions(role.getId()));
            return bo;
        }).toList();
    }

    @Override
    public List<Permission> getRolePermissions(Long roleId) {
        return QueryChain.of(permissionMapper)
                .where(PERMISSION.ID.in(//把中间表进行嵌套查询
                        QueryChain.of(rolePermissionMapper)
                                .from(ROLE_PERMISSION)
                                .select(ROLE_PERMISSION.PERMISSION_ID)
                                .where(ROLE_PERMISSION.ROLE_ID.eq(roleId))
                ))
                .list();
    }

    @Override
    public Void setRolePermissionToRedis() {

        AtomicInteger roleCount = new AtomicInteger(0);
        AtomicInteger permissionCount = new AtomicInteger(0);
        
        List<Role> roleList = this.list();
        List<RolePermissionBO> permissionBOS = this.buildRolePermissions(roleList);
        
        
        permissionBOS.forEach(bo->{
            // 获取当前角色的所有权限代码
            List<String> permissionCodes = bo.getPermissions().stream()
                    .map(Permission::getCode)
                    .toList();
            
            
            String redisKey=RedisKeys.ROLE_PERMISSION_KEY_PREFIX +bo.getRole().getName();
            
            if(!permissionCodes.isEmpty()){
                
                //权限转化成数组以构建set插入redis
                Long addedCount = redisTemplate.opsForSet().add(redisKey, permissionCodes.toArray(new String[0]));

                // 如果成功添加了权限，则进行计数
                if (addedCount != null && addedCount > 0) {
                    roleCount.incrementAndGet(); // 成功添加权限的角色数 +1
                    permissionCount.addAndGet(addedCount.intValue()); // 成功添加的权限总数
                }
            }
        });
        
        log.info("更新了 {} 个角色的 {} 个权限到 Redis 中", roleCount.get(), permissionCount.get());
        
        return null;
    }

    @Override
    public Boolean addRoleForUser(Long uid, Long roleId) {
        int row = userRoleMapper.insert(new UserRole(uid, roleId));
        return row>0;
    }
}
