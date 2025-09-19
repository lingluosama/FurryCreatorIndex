package org.rookie.data.service;

import org.rookie.annotation.RedisCache;
import org.rookie.annotation.UpdateCache;
import org.rookie.model.bo.CommentBO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.entity.database.Comment;
import org.rookie.model.entity.database.Tag;
import org.rookie.model.form.CommentForm;
import org.rookie.model.form.EntityTagsForm;
import org.rookie.model.form.TagForm;
import org.rookie.model.query.EntityCommentPageQuery;

import java.util.List;

public interface TagCommentService {

    Tag createTag(TagForm tag);

    Boolean deleteTag(Long tagId);

    List<Tag> getAllTags();


    List<Tag> getTagsByEntityId(Long entityId, String entityType);

    void overwriteEntityTags(EntityTagsForm form);

    Comment createComment(CommentForm comment);
    
    @RedisCache(key="EntityComment:",expire = 1)
    PageResult<CommentBO> getCommentsByEntityId(EntityCommentPageQuery query);
    
    @UpdateCache()
    Boolean deleteComment(Long commentId);
}