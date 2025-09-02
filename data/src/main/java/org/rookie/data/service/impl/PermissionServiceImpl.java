package org.rookie.data.service.impl;

import cn.hutool.core.lang.Pair;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rookie.data.converter.PermissionConverter;
import org.rookie.data.mapper.PermissionMapper;
import org.rookie.data.mapper.RolePermissionMapper;
import org.rookie.data.service.IPermissionService;
import org.rookie.data.utils.DataUtils;
import org.rookie.model.dto.PageResult;
import org.rookie.model.entity.database.Permission;
import org.rookie.model.entity.database.RolePermission;
import org.rookie.model.entity.database.table.PermissionTableDef;
import org.rookie.model.entity.database.table.RolePermissionTableDef;
import org.rookie.model.form.PermissionForm;
import org.rookie.model.form.RoleForm;
import org.rookie.model.query.PermissionQuery;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    private final PermissionConverter converter;
    private final RolePermissionMapper rolePermissionMapper;


    @Override
    public Boolean createPermission(PermissionForm form) {
        Permission permission = converter.formToPermission(form);
        return this.save(permission);
    }

    @Override
    public Boolean updateRolePermission(List<Long> permissionIds, Long roleId) {
        List<Long> oldIds = QueryChain.of(rolePermissionMapper)
                .select(RolePermissionTableDef.ROLE_PERMISSION.PERMISSION_ID)
                .where(RolePermissionTableDef.ROLE_PERMISSION.ROLE_ID.eq(roleId))
                .list().stream()
                .map(RolePermission::getPermissionId).toList();

        Pair<Set<Long>, Set<Long>> setPair = DataUtils.getSetAdd(oldIds, permissionIds);
        Set<Long> toDelete = setPair.getKey();
        Set<Long> toAdd = setPair.getValue();

        int deleted = rolePermissionMapper.deleteByCondition(
                RolePermissionTableDef.ROLE_PERMISSION.ROLE_ID.eq(roleId)
                        .and(RolePermissionTableDef.ROLE_PERMISSION.PERMISSION_ID.in(toDelete)));

        int inserted = rolePermissionMapper.insertBatch(toAdd.stream().map(permissionId ->
            new RolePermission(roleId,permissionId)
        ).toList());
        log.info("用户组id:{}已更新，移除{}个权限，新增{}个权限",roleId,deleted,inserted);

        return toDelete.size()==deleted && toAdd.size()==inserted;
    }


    @Override
    public PageResult<Permission> searchPermission(PermissionQuery query) {
        Page<Permission> permissionPage = QueryChain.of(this.mapper)
                .where(PermissionTableDef.PERMISSION.CODE.like(query.getKeyword())
                        .or(PermissionTableDef.PERMISSION.DESCRIPTION.like(query.getKeyword())))
                .page(new Page<>(query.getPageNumber(), query.getPageSize()));

        PageResult<Permission> result = new PageResult<>();
        result.setTotal(permissionPage.getTotalRow());
        result.setRecords(permissionPage.getRecords());

        return result;
    }
}
