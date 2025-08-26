package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class EntityTagTableDef extends TableDef {

    public static final EntityTagTableDef ENTITY_TAG = new EntityTagTableDef();

    public final QueryColumn TAG_ID = new QueryColumn(this, "tag_id");

    public final QueryColumn ENTITY_ID = new QueryColumn(this, "entity_id");

    public final QueryColumn ENTITY_TYPE = new QueryColumn(this, "entity_type");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{TAG_ID, ENTITY_ID, ENTITY_TYPE};

    public EntityTagTableDef() {
        super("", "entity_tag");
    }

    private EntityTagTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public EntityTagTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new EntityTagTableDef("", "entity_tag", alias));
    }

}
