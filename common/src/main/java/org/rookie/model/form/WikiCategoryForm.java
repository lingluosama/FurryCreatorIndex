package org.rookie.model.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WikiCategoryForm {
    String name;

    Long parentId;

    String description;

    Integer sortOrder;

}
