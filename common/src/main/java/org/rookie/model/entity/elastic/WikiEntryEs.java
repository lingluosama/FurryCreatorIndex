package org.rookie.model.entity.elastic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class WikiEntryEs {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("cover_image_url")
    private String coverImageUrl;

    @JsonProperty("status")
    private String status;

    @JsonProperty("view_count")
    private Long viewCount;

    @JsonProperty("created_by")
    private Long createdBy;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("updated_by")
    private Long updatedBy;

    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    /**
     * Elasticsearch 中的标签字段，
     * 对应 Elasticsearch 中的 `keyword` 数组类型。
     */
    @JsonProperty("tags")
    private List<String> tags;
}