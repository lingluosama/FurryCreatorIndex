package org.rookie.model.form;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EntityTagsForm {
    String entityType;

    List<Long> tagIds;

    Long entityId;

}
