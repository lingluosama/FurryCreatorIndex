package org.rookie.model.dto;


import io.micrometer.core.instrument.Tags;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WikiEntryDetailDTO {
    Long Id;
    String title;
    String slug;
    String content;
    String coverImageUrl;
    String status;
    Long viewCount;
    String createAt;
    String updateAt;

    String category;
    String creator;//上传用户名
    String creatorId;
    String creatorAvatarUrl;
    Integer versionNumber;
    String updater;//更新用户名
    String updaterId;
    String updaterAvatarUrl;
    List<Tags> tags;
    String comment;//版本修改说明
}
