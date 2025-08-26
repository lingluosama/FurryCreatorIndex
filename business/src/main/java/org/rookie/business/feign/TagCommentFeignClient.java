package org.rookie.business.feign;

import org.rookie.business.config.FeignConfig;
import org.rookie.consts.Result;
import org.rookie.model.bo.CommentBO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.entity.database.Comment;
import org.rookie.model.entity.database.Tag;
import org.rookie.model.form.CommentForm;
import org.rookie.model.form.EntityTagsForm;
import org.rookie.model.form.TagForm;
import org.rookie.model.query.EntityCommentPageQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "data-service",path = "/data",configuration = FeignConfig.class)
public interface TagCommentFeignClient {
    @PostMapping("/tags")
    Result<Tag> createTag(@RequestBody TagForm form);

    @GetMapping("/tags")
    Result<List<Tag>> getAllTags();

    @PostMapping("/{entityId}/tags/{entityType}")
    Result<List<Tag>> getTagsByEntityId(@PathVariable("entityId") Long entityId, @PathVariable String entityType);

    @PostMapping("/entities/tags")
    Result<Void> overwriteEntityTags(@RequestBody EntityTagsForm form);

    @PostMapping("/comments")
    Result<Comment> createComment(@RequestBody CommentForm form);

    @PostMapping("/comments/entity")
    Result<PageResult<CommentBO>> getCommentsByEntityId(@RequestBody EntityCommentPageQuery query);

    @DeleteMapping("/comments/{id}")
    Result<Void> deleteComment(@PathVariable("id") Long id);
}
