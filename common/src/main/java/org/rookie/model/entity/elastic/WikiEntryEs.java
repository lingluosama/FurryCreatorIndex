package org.rookie.model.entity.elastic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

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

    // 注意：created_at 在 JSON 中是时间戳（Long），所以这里要用 Long
    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("updated_at")
    private Long updatedAt;

    @JsonProperty("updated_by")
    private Long updatedBy;

    // 注意：is_deleted 在 JSON 中是 0 或 1（Integer），所以这里要用 Integer
    @JsonProperty("is_deleted")
    private Integer isDeleted;

    @JsonProperty("tags")
    private List<String> tags;
}