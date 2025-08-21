package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class UserTableDef extends TableDef {

    public static final UserTableDef USER = new UserTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    public final QueryColumn STATUS = new QueryColumn(this, "status");

    public final QueryColumn NICKNAME = new QueryColumn(this, "nickname");

    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    public final QueryColumn AVATAR_URL = new QueryColumn(this, "avatar_url");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn UPDATED_AT = new QueryColumn(this, "updated_at");

    public final QueryColumn LAST_LOGIN_AT = new QueryColumn(this, "last_login_at");

    public final QueryColumn PHONE_NUMBER = new QueryColumn(this, "phone_number");

    public final QueryColumn PASSWORD_HASH = new QueryColumn(this, "password_hash");

    public final QueryColumn REGISTRATION_IP = new QueryColumn(this, "registration_ip");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, EMAIL, STATUS, NICKNAME, USERNAME, AVATAR_URL, CREATED_AT, UPDATED_AT, LAST_LOGIN_AT, PHONE_NUMBER, PASSWORD_HASH, REGISTRATION_IP};

    public UserTableDef() {
        super("", "user");
    }

    private UserTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public UserTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new UserTableDef("", "user", alias));
    }

}
