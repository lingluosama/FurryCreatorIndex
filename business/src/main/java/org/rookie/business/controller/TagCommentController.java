package org.rookie.business.controller;


import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagCommentController {
    private final TagCommentService tagCommentService;

    @PostMapping("/tags")
    public Result<Tag> createTag(TagForm form){
        Tag tag = tagCommentService.createTag(form);
        return Result.success(tag);
    }

    @GetMapping("/tags")
    public Result<List<Tag>> getAllTags(){
        List<Tag> tags = tagCommentService.getAllTags();
        return Result.success(tags);
    }

    @PostMapping("/{entityId}/tags/{entityType}")
    public Result<List<Tag>> getTagsByEntityId(@PathVariable Long entityId ,@PathVariable String entityType){
        List<Tag> tagsByEntityId = tagCommentService.getTagsByEntityId(entityId, entityType);
        return Result.success(tagsByEntityId);
    }

    @PostMapping("/entities/tags")
    public Result<Void> overwriteEntityTags(EntityTagsForm form){
        tagCommentService.overwriteEntityTags(form);
        return Result.success();
    }

    @PostMapping("/comments")
    public Result<Comment> createComment(CommentForm form){
        Comment comment = tagCommentService.createComment(form);
        return Result.success(comment);
    }

    @PostMapping("/comments/entity")
    Result<PageResult<CommentBO> > getCommentsByEntityId(EntityCommentPageQuery query){
        PageResult<CommentBO> comments = tagCommentService.getCommentsByEntityId(query);
        return Result.success(comments);
    }

    @DeleteMapping("/comments/{id}")
    public Result<Void> deleteComment(@PathVariable("id") Long commentId){
        tagCommentService.deleteComment(commentId);
        return Result.success();
    }

}
