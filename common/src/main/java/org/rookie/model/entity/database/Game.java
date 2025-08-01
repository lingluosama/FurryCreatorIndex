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
@Table("game")
public class Game implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Long id;

    private String title;

    private String platform;

    private String genre;

    private LocalDate releaseDate;

    private Long developerId;

    private Long publisherId;

    private String description;

    private String coverImageUrl;

    private String officialWebsiteUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}