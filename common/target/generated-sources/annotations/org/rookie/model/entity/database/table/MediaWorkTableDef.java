package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class MediaWorkTableDef extends TableDef {

    public static final MediaWorkTableDef MEDIA_WORK = new MediaWorkTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn TYPE = new QueryColumn(this, "type");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn STUDIO_ID = new QueryColumn(this, "studio_id");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn UPDATED_AT = new QueryColumn(this, "updated_at");

    public final QueryColumn DIRECTOR_ID = new QueryColumn(this, "director_id");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn RELEASE_DATE = new QueryColumn(this, "release_date");

    public final QueryColumn COVER_IMAGE_URL = new QueryColumn(this, "cover_image_url");

    public final QueryColumn OFFICIAL_WEBSITE_URL = new QueryColumn(this, "official_website_url");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TYPE, TITLE, STUDIO_ID, CREATED_AT, UPDATED_AT, DIRECTOR_ID, DESCRIPTION, RELEASE_DATE, COVER_IMAGE_URL, OFFICIAL_WEBSITE_URL};

    public MediaWorkTableDef() {
        super("", "media_work");
    }

    private MediaWorkTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MediaWorkTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MediaWorkTableDef("", "media_work", alias));
    }

}
