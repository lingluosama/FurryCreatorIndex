package org.rookie.model.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WikiEntryPageQuery extends BasePager implements CacheKeyProvider{
    String keyword;

    String status;

    Long categoryId;

    @Override
    public String toCacheKey() {
        // 手动拼接，确保字段顺序和null值处理
        return String.format(
                "keyword:%s|status:%s|categoryId:%s|pageSize:%d|pageNumber:%d",
                this.keyword != null ? this.keyword : "null",
                this.status != null ? this.status : "null",
                this.categoryId != null ? this.categoryId : "null",
                this.getPageSize(),
                this.getPageNumber()
        );
    }
}
