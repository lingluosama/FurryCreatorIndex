package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class CreatorTableDef extends TableDef {

    public static final CreatorTableDef CREATOR = new CreatorTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn BIO = new QueryColumn(this, "bio");

    public final QueryColumn NAME = new QueryColumn(this, "name");

    public final QueryColumn STATUS = new QueryColumn(this, "status");

    public final QueryColumn AVATAR_URL = new QueryColumn(this, "avatar_url");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn UPDATED_AT = new QueryColumn(this, "updated_at");

    public final QueryColumn WEBSITE_URL = new QueryColumn(this, "website_url");

    public final QueryColumn SOCIAL_LINKS = new QueryColumn(this, "social_links");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, BIO, NAME, STATUS, AVATAR_URL, CREATED_AT, UPDATED_AT, WEBSITE_URL, SOCIAL_LINKS};

    public CreatorTableDef() {
        super("", "creator");
    }

    private CreatorTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public CreatorTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new CreatorTableDef("", "creator", alias));
    }

}
