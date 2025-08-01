package org.rookie.model.entity.database;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data; // 引入 Lombok 的 Data 注解

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data 
@Table("media_work")
public class MediaWork implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Long id;

    private String title;

    private String type; // 例如 ANIME, MOVIE, TV_SERIES

    private LocalDate releaseDate;

    private Long directorId;

    private Long studioId;

    private String description;

    private String coverImageUrl;

    private String officialWebsiteUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}