package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class LiteratureWorkTableDef extends TableDef {

    /**
     * &#064;description  漫画小说实体类
     */
    public static final LiteratureWorkTableDef LITERATURE_WORK = new LiteratureWorkTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn TYPE = new QueryColumn(this, "type");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn AUTHOR_ID = new QueryColumn(this, "author_id");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn UPDATED_AT = new QueryColumn(this, "updated_at");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn PUBLISHER_ID = new QueryColumn(this, "publisher_id");

    public final QueryColumn RELEASE_DATE = new QueryColumn(this, "release_date");

    public final QueryColumn COVER_IMAGE_URL = new QueryColumn(this, "cover_image_url");

    public final QueryColumn ILLUSTRATOR_ID = new QueryColumn(this, "illustrator_id");

    public final QueryColumn OFFICIAL_WEBSITE_URL = new QueryColumn(this, "official_website_url");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TYPE, TITLE, AUTHOR_ID, CREATED_AT, UPDATED_AT, DESCRIPTION, PUBLISHER_ID, RELEASE_DATE, COVER_IMAGE_URL, ILLUSTRATOR_ID, OFFICIAL_WEBSITE_URL};

    public LiteratureWorkTableDef() {
        super("", "literature_work");
    }

    private LiteratureWorkTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public LiteratureWorkTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new LiteratureWorkTableDef("", "literature_work", alias));
    }

}
