package org.rookie.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rookie.model.entity.database.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class WikiEntryBO {
    Long id;
    String title;
    String slug;
    String content;//部份
    String coverImageUrl;
    String status;
    Long viewCount;
    Long updateBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    List<String>tags;

}
