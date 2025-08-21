package org.rookie.business.controller;


import lombok.RequiredArgsConstructor;
import org.rookie.consts.Result;
import org.rookie.model.entity.database.WikiEntry;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wiki-entries")
@RequiredArgsConstructor
public class WikiEntryController {

    @PostMapping
    public Result<Void> createWikiEntry(WikiEntry wikiEntry) {

        return null;
    }


}
