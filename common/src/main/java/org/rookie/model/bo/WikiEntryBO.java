package org.rookie.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rookie.model.entity.database.Tag;

import java.util.List;

@Data
@NoArgsConstructor
public class WikiEntryBO {
    String title;
    String slug;
    String content;//部份
    String coverImageUrl;
    String status;
    Long viewCount;
    String createAt;
    String updateAt;

    List<String>tags;

}
