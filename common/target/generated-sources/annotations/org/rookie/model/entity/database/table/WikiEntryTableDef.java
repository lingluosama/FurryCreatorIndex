package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class WikiEntryTableDef extends TableDef {

    public static final WikiEntryTableDef WIKI_ENTRY = new WikiEntryTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn SLUG = new QueryColumn(this, "slug");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn STATUS = new QueryColumn(this, "status");

    public final QueryColumn CONTENT = new QueryColumn(this, "content");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn CREATED_BY = new QueryColumn(this, "created_by");

    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    public final QueryColumn UPDATED_AT = new QueryColumn(this, "updated_at");

    public final QueryColumn UPDATED_BY = new QueryColumn(this, "updated_by");

    public final QueryColumn VIEW_COUNT = new QueryColumn(this, "view_count");

    public final QueryColumn CATEGORY_ID = new QueryColumn(this, "category_id");

    public final QueryColumn COVER_IMAGE_URL = new QueryColumn(this, "cover_image_url");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, SLUG, TITLE, STATUS, CONTENT, CREATED_AT, CREATED_BY, IS_DELETED, UPDATED_AT, UPDATED_BY, VIEW_COUNT, CATEGORY_ID, COVER_IMAGE_URL};

    public WikiEntryTableDef() {
        super("", "wiki_entry");
    }

    private WikiEntryTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public WikiEntryTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new WikiEntryTableDef("", "wiki_entry", alias));
    }

}
