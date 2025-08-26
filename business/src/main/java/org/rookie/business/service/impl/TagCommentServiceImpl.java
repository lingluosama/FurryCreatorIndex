package org.rookie.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.rookie.business.feign.TagCommentFeignClient;
import org.rookie.business.service.TagCommentService;
import org.rookie.consts.Result;
import org.rookie.model.bo.CommentBO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.entity.database.Comment;
import org.rookie.model.entity.database.Tag;
import org.rookie.model.form.CommentForm;
import org.rookie.model.form.EntityTagsForm;
import org.rookie.model.form.TagForm;
import org.rookie.model.query.EntityCommentPageQuery;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TagCommentServiceImpl implements TagCommentService {

    private final TagCommentFeignClient tagCommentFeignClient;

    @Override
    public Tag createTag(TagForm form) {
        Result<Tag> result = tagCommentFeignClient.createTag(form);

        return result.getData();
    }

    @Override
    public List<Tag> getAllTags() {
        Result<List<Tag>> result = tagCommentFeignClient.getAllTags();

        return result.getData();
    }

    @Override
    public List<Tag> getTagsByEntityId(Long entityId, String entityType) {
        Result<List<Tag>> result = tagCommentFeignClient.getTagsByEntityId(entityId, entityType);

        return result.getData();
    }

    @Override
    public Void overwriteEntityTags(EntityTagsForm form) {
        Result<Void> result = tagCommentFeignClient.overwriteEntityTags(form);

        return result.getData();
    }

    @Override
    public Comment createComment(CommentForm form) {
        Result<Comment> result = tagCommentFeignClient.createComment(form);
        return result.getData();
    }

    @Override
    public PageResult<CommentBO> getCommentsByEntityId(EntityCommentPageQuery query) {
        Result<PageResult<CommentBO>> result = tagCommentFeignClient.getCommentsByEntityId(query);
        return result.getData();
    }

    @Override
    public Void deleteComment(Long commentId) {
        Result<Void> result = tagCommentFeignClient.deleteComment(commentId);
        return result.getData();
    }
}
