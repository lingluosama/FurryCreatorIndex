package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class WikiCategoryTableDef extends TableDef {

    public static final WikiCategoryTableDef WIKI_CATEGORY = new WikiCategoryTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn NAME = new QueryColumn(this, "name");

    public final QueryColumn PARENT_ID = new QueryColumn(this, "parent_id");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn SORT_ORDER = new QueryColumn(this, "sort_order");

    public final QueryColumn UPDATED_AT = new QueryColumn(this, "updated_at");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, PARENT_ID, CREATED_AT, SORT_ORDER, UPDATED_AT, DESCRIPTION};

    public WikiCategoryTableDef() {
        super("", "wiki_category");
    }

    private WikiCategoryTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public WikiCategoryTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new WikiCategoryTableDef("", "wiki_category", alias));
    }

}
