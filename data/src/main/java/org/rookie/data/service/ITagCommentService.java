package org.rookie.data.service;

import org.rookie.model.bo.CommentBO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.entity.database.Comment;
import org.rookie.model.entity.database.Tag;
import org.rookie.model.form.CommentForm;
import org.rookie.model.form.TagForm;
import org.rookie.model.query.EntityCommentPageQuery;

import java.util.List;

public interface ITagCommentService {

    Tag createTag(TagForm tag);

    Boolean deleteTag(Long tagId);

    List<Tag> getAllTags();

    List<Tag> getTagsByEntityId(Long entityId, String entityType);

    Void overwriteEntityTags(Long entityId, String entityType, List<Long> tagIds);

    Comment createComment(CommentForm comment);

    PageResult<CommentBO> getCommentsByEntityId(EntityCommentPageQuery query);


}