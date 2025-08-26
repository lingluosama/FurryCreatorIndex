package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class WorkTableTableDef extends TableDef {

    public static final WorkTableTableDef WORK_TABLE = new WorkTableTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn CID = new QueryColumn(this, "cid");

    public final QueryColumn LINK = new QueryColumn(this, "link");

    public final QueryColumn TYPE = new QueryColumn(this, "type");

    public final QueryColumn COVER = new QueryColumn(this, "cover");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn LANGUAGE = new QueryColumn(this, "language");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn PUBLISH_TIME = new QueryColumn(this, "publish_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CID, LINK, TYPE, COVER, TITLE, LANGUAGE, DESCRIPTION, PUBLISH_TIME};

    public WorkTableTableDef() {
        super("", "work");
    }

    private WorkTableTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public WorkTableTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new WorkTableTableDef("", "work", alias));
    }

}
