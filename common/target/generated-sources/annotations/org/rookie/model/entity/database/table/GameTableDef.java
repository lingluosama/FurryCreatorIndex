package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class GameTableDef extends TableDef {

    public static final GameTableDef GAME = new GameTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn GENRE = new QueryColumn(this, "genre");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn PLATFORM = new QueryColumn(this, "platform");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn UPDATED_AT = new QueryColumn(this, "updated_at");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn DEVELOPER_ID = new QueryColumn(this, "developer_id");

    public final QueryColumn PUBLISHER_ID = new QueryColumn(this, "publisher_id");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, GENRE, TITLE, PLATFORM, CREATED_AT, UPDATED_AT, DESCRIPTION, DEVELOPER_ID, PUBLISHER_ID, RELEASE_DATE, COVER_IMAGE_URL, OFFICIAL_WEBSITE_URL};

    public GameTableDef() {
        super("", "game");
    }

    private GameTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public GameTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new GameTableDef("", "game", alias));
    }

}
