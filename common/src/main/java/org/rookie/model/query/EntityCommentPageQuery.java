package org.rookie.model.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EntityCommentPageQuery extends BasePager{
    Long entityId;
    String entityType;
}
