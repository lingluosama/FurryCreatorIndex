package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class UserTableTableDef extends TableDef {

    public static final UserTableTableDef USER_TABLE = new UserTableTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn NAME = new QueryColumn(this, "name");

    public final QueryColumn SALT = new QueryColumn(this, "salt");

    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");

    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    public final QueryColumn SIGNATURE = new QueryColumn(this, "signature");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, SALT, EMAIL, AVATAR, PASSWORD, SIGNATURE};

    public UserTableTableDef() {
        super("", "user");
    }

    private UserTableTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public UserTableTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new UserTableTableDef("", "user", alias));
    }

}
