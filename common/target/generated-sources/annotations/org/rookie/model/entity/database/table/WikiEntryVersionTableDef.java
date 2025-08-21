package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class WikiEntryVersionTableDef extends TableDef {

    public static final WikiEntryVersionTableDef WIKI_ENTRY_VERSION = new WikiEntryVersionTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn COMMENT = new QueryColumn(this, "comment");

    public final QueryColumn CONTENT = new QueryColumn(this, "content");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn CREATED_BY = new QueryColumn(this, "created_by");

    public final QueryColumn WIKI_ENTRY_ID = new QueryColumn(this, "wiki_entry_id");

    public final QueryColumn VERSION_NUMBER = new QueryColumn(this, "version_number");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, COMMENT, CONTENT, CREATED_AT, CREATED_BY, WIKI_ENTRY_ID, VERSION_NUMBER};

    public WikiEntryVersionTableDef() {
        super("", "wiki_entry_version");
    }

    private WikiEntryVersionTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public WikiEntryVersionTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new WikiEntryVersionTableDef("", "wiki_entry_version", alias));
    }

}
