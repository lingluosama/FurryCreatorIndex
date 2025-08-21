package org.rookie.model.entity.database.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class CreatorTableTableDef extends TableDef {

    public static final CreatorTableTableDef CREATOR_TABLE = new CreatorTableTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn BLI = new QueryColumn(this, "bli");

    public final QueryColumn NAME = new QueryColumn(this, "name");

    public final QueryColumn TYPE = new QueryColumn(this, "type");

    public final QueryColumn PIXIV = new QueryColumn(this, "pixiv");

    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");

    public final QueryColumn GITHUB = new QueryColumn(this, "github");

    public final QueryColumn TWITTER = new QueryColumn(this, "twitter");

    public final QueryColumn YOUTUBE = new QueryColumn(this, "youtube");

    public final QueryColumn INFORMATION = new QueryColumn(this, "information");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, BLI, NAME, TYPE, PIXIV, AVATAR, GITHUB, TWITTER, YOUTUBE, INFORMATION};

    public CreatorTableTableDef() {
        super("", "creator");
    }

    private CreatorTableTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public CreatorTableTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new CreatorTableTableDef("", "creator", alias));
    }

}
