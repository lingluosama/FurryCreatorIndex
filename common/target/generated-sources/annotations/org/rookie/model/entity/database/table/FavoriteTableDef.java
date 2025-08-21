package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class FavoriteTableDef extends TableDef {

    public static final FavoriteTableDef FAVORITE = new FavoriteTableDef();

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn ENTITY_ID = new QueryColumn(this, "entity_id");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn ENTITY_TYPE = new QueryColumn(this, "entity_type");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{USER_ID, ENTITY_ID, CREATED_AT, ENTITY_TYPE};

    public FavoriteTableDef() {
        super("", "favorite");
    }

    private FavoriteTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public FavoriteTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new FavoriteTableDef("", "favorite", alias));
    }

}
