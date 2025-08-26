package org.rookie.model.form;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class WikiEntryForm {
    Long id;

    String title;

    String slug;//条目页面对外访问地址

    String categoryId;

    String content;

    MultipartFile coverImage;

    String coverImageUrl;

    List<Long> tagIds;


    String status;

    Long createdBy;

    Long updatedBy;
}
