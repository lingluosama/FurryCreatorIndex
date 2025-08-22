package org.rookie.data.controller;

import lombok.RequiredArgsConstructor;
import org.rookie.consts.Result;
import org.rookie.data.service.ITagCommentService;
import org.rookie.model.entity.database.Tag;
import org.rookie.model.form.TagForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/data")
@RequiredArgsConstructor
public class TagCommentController {

    private final ITagCommentService tagCommentService;

    @PostMapping("/tags")
    Result<Tag> createTag(TagForm form){
        Tag tag = tagCommentService.createTag(form);
        return Result.success(tag);
    }

    @GetMapping("/tags")
    Result<List<Tag>> getAllTags(){
        List<Tag> tags = tagCommentService.getAllTags();
        return Result.success(tags);
    }


}
