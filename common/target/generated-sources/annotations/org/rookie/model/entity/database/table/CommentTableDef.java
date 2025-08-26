package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class CommentTableDef extends TableDef {

    public static final CommentTableDef COMMENT = new CommentTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn STATUS = new QueryColumn(this, "status");

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn CONTENT = new QueryColumn(this, "content");

    public final QueryColumn ENTITY_ID = new QueryColumn(this, "entity_id");

    public final QueryColumn PARENT_ID = new QueryColumn(this, "parent_id");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn UPDATED_AT = new QueryColumn(this, "updated_at");

    public final QueryColumn ENTITY_TYPE = new QueryColumn(this, "entity_type");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, STATUS, USER_ID, CONTENT, ENTITY_ID, PARENT_ID, CREATED_AT, UPDATED_AT, ENTITY_TYPE};

    public CommentTableDef() {
        super("", "comment");
    }

    private CommentTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public CommentTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new CommentTableDef("", "comment", alias));
    }

}
