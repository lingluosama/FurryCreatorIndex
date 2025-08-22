package org.rookie.model.form;

import lombok.Data;

@Data
public class CommentForm {
    private Long entityId;
    private Long entityType;
    private Long userId;
    private Long parentId;
    private String content;

}
