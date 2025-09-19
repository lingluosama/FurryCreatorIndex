package org.rookie.model.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EntityCommentPageQuery extends BasePager implements CacheKeyProvider{
    Long entityId;
    String entityType;

    @Override
    public String toCacheKey() {
        // 手动拼接，确保字段顺序和null值处理
        return String.format(
                "entityId:%s|entityType:%s|pageSize:%d|pageNumber:%d",
                this.entityId != null ? this.entityId : "null",
                this.entityType != null ? this.entityType : "null",
                this.getPageSize(),
                this.getPageNumber()
        );
    }
}