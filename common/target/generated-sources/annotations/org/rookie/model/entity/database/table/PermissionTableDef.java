package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class PermissionTableDef extends TableDef {

    public static final PermissionTableDef PERMISSION = new PermissionTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn CODE = new QueryColumn(this, "code");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CODE, CREATED_AT, DESCRIPTION};

    public PermissionTableDef() {
        super("", "permission");
    }

    private PermissionTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public PermissionTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new PermissionTableDef("", "permission", alias));
    }

}
