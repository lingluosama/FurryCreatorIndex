package org.rookie.model.form;

import lombok.Data;

import java.util.List;

@Data
public class EntityTagForm {
    private Long entityId;
    private List<Long> tagIds;
    private String entityType;

}
