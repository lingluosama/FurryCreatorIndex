package org.rookie.data.controller;

import lombok.RequiredArgsConstructor;
import org.rookie.consts.Result;
import org.rookie.data.service.TagCommentService;
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
@RequestMapping("/data")
@RequiredArgsConstructor
public class TagCommentController {

    private final TagCommentService tagCommentService;

    @PostMapping("/tags")
    Result<Tag> createTag(@RequestBody TagForm form){
        Tag tag = tagCommentService.createTag(form);
        return Result.success(tag);
    }

    @GetMapping("/tags")
    Result<List<Tag>> getAllTags(){
        List<Tag> tags = tagCommentService.getAllTags();
        return Result.success(tags);
    }

    @GetMapping("/{entityId}/tags/{entityType}")
    Result<List<Tag>> getTagsByEntityId(@PathVariable Long entityId,@PathVariable String entityType){
        List<Tag> tags = tagCommentService.getTagsByEntityId(entityId, entityType);
        return Result.success(tags);
    }

    @PostMapping("/entities/tags")
    Result<Void> overwriteEntityTags(@RequestBody EntityTagsForm form){
        tagCommentService.overwriteEntityTags(form);
        return Result.success();
    }

    @PostMapping("/comments")
    Result<Comment> createComment(@RequestBody CommentForm form){
        Comment comment = tagCommentService.createComment(form);
         return Result.success(comment);
    }

    @GetMapping("/comments/entity")
    Result<PageResult<CommentBO>> getCommentsByEntityId(@RequestBody EntityCommentPageQuery query){
        PageResult<CommentBO> comments = tagCommentService.getCommentsByEntityId(query);
        return Result.success(comments);
    }

    @DeleteMapping("/comments/{id}")
    Result<Void> deleteComment(@PathVariable Long id){
        tagCommentService.deleteComment(id);
        return Result.success();
    }


}
