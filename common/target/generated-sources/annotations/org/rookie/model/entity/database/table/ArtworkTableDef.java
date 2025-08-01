package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class ArtworkTableDef extends TableDef {

    public static final ArtworkTableDef ARTWORK = new ArtworkTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn ARTIST_ID = new QueryColumn(this, "artist_id");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn IMAGE_URLS = new QueryColumn(this, "image_urls");

    public final QueryColumn UPDATED_AT = new QueryColumn(this, "updated_at");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TITLE, ARTIST_ID, CREATED_AT, IMAGE_URLS, UPDATED_AT, DESCRIPTION};

    public ArtworkTableDef() {
        super("", "artwork");
    }

    private ArtworkTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ArtworkTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ArtworkTableDef("", "artwork", alias));
    }

}
