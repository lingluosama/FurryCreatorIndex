package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class ReportTableDef extends TableDef {

    public static final ReportTableDef REPORT = new ReportTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn REASON = new QueryColumn(this, "reason");

    public final QueryColumn STATUS = new QueryColumn(this, "status");

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn ENTITY_ID = new QueryColumn(this, "entity_id");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn HANDLED_AT = new QueryColumn(this, "handled_at");

    public final QueryColumn HANDLED_BY = new QueryColumn(this, "handled_by");

    public final QueryColumn ENTITY_TYPE = new QueryColumn(this, "entity_type");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, REASON, STATUS, USER_ID, ENTITY_ID, CREATED_AT, HANDLED_AT, HANDLED_BY, ENTITY_TYPE, DESCRIPTION};

    public ReportTableDef() {
        super("", "report");
    }

    private ReportTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ReportTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ReportTableDef("", "report", alias));
    }

}
