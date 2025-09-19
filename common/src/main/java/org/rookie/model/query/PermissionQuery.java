package org.rookie.model.query;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionQuery extends BasePager implements CacheKeyProvider{
    String keyword;

    @Override
    public String toCacheKey() {
        // 手动拼接，确保字段顺序和null值处理
        return String.format(
                "keyword:%s|pageSize:%d|pageNumber:%d",
                this.keyword != null ? this.keyword : "null",
                this.getPageSize(),
                this.getPageNumber()
        );
    }
}