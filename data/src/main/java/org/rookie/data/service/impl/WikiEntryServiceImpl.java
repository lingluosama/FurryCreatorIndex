package org.rookie.data.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.alibaba.fastjson2.JSON;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.rookie.data.converter.WikiEntryConverter;
import org.rookie.data.mapper.DraftMapper;
import org.rookie.data.mapper.EntityTagMapper;
import org.rookie.data.mapper.WikiEntryMapper;
import org.rookie.data.mapper.WikiEntryVersionMapper;
import org.rookie.data.service.IDraftService;
import org.rookie.data.service.IWikiEntryService;
import org.rookie.data.service.TagCommentService;
import org.rookie.data.utils.EsRespHandler;
import org.rookie.exception.BusinessException;
import org.rookie.model.bo.WikiEntryBO;
import org.rookie.model.dto.DraftSubmitConflictDTO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.dto.WikiEntryDetailDTO;
import org.rookie.model.entity.database.*;
import org.rookie.model.entity.database.table.EntityTagTableDef;
import org.rookie.model.entity.database.table.WikiEntryVersionTableDef;
import org.rookie.model.entity.elastic.WikiEntryEs;
import org.rookie.model.form.WikiEntryForm;
import org.rookie.model.query.WikiEntryPageQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class WikiEntryServiceImpl extends ServiceImpl<WikiEntryMapper, WikiEntry> implements IWikiEntryService {

    private final WikiEntryConverter converter;

    private final WikiEntryVersionMapper versionMapper;

    private final ElasticsearchClient esClient;

    private final EsRespHandler esRespHandler;

    private final EntityTagMapper entityTagMapper;

    private final TagCommentService tagCommentService;
    
    private final IDraftService draftService;
    
    @Override
    public WikiEntry createWikiEntry(WikiEntryForm form) {

        WikiEntry entity = converter.toEntity(form);
        boolean saved = this.save(entity);
        if(saved){
            WikiEntryVersion versionEntity = converter.toVersionEntity(entity);
            versionEntity.setVersionNumber(1);
            versionEntity.setComment("首次创建版本");
            int insert = versionMapper.insert(versionEntity);
            if(!(insert >0)){
                throw new BusinessException(HttpStatus.SC_BAD_REQUEST,"保存条目历史记录失败");
            }
            return entity;
        }else{
            throw new BusinessException(HttpStatus.SC_BAD_REQUEST,"保存条目失败");
        }

    }

    @Override
    public PageResult<WikiEntryBO> queryWikiEntry(WikiEntryPageQuery query) {
        int from = (query.getPageNumber() - 1) * query.getPageSize();
        int size = query.getPageSize();

        SearchRequest request = SearchRequest.of(s -> s
                .index("mysql-wiki-v3.testdb.wiki_entry")
                .from(from)
                .size(size)
                .query(q -> q.bool(b -> {
                    b.must(m -> m.multiMatch(mm ->
                            mm.query(query.getKeyword()).fields("after.title", "after.content")
                    ));
                    if (query.getCategoryId() != null) {
                        b.filter(f -> f.term(t -> t.field("after.category_id").value(String.valueOf(query.getCategoryId()))));
                    }
                    return b;
                }))
        );

        try {
            SearchResponse<Map> response = esClient.search(request, Map.class);
            List<WikiEntryEs> wikiEntryEs = esRespHandler.extractHistFromAfterFiled(response, WikiEntryEs.class);
            long hits = esRespHandler.getTotalHits(response);
            List<WikiEntryBO> entryBOS = converter.toBOList(wikiEntryEs);
            return new PageResult<>(hits,entryBOS);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (Exception e){
            log .error("查询条目异常",e);
            return new PageResult<>();
        }

    }

    @Override
    public WikiEntryDetailDTO getWikiEntryById(Long id) {
        WikiEntry wikiEntry = this.getMapper().selectOneWithRelationsById(id);
        List<Tag> tags = tagCommentService.getTagsByEntityId(id, "wiki_entry");
        WikiEntryDetailDTO dto = converter.toDetailDTO(wikiEntry);
        dto.setTags(tags);
        WikiEntryVersion latestVersion = QueryChain.of(versionMapper).select(WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.COMMENT)
                .where(WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.WIKI_ENTRY_ID.eq(id))
                .orderBy(WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.VERSION_NUMBER.desc())
                .one();
        dto.setComment(latestVersion.getComment());
        return dto;
    }

    @Override
    public Boolean deleteWikiEntry(Long id) {
        WikiEntry entry = new WikiEntry();
        entry.setId(id);
        entry.setIsDeleted(true);
        return this.updateById(entry);
    }

    @Override
    public Boolean submitNewEntryVersion(WikiEntryForm form) {
        WikiEntry entity = converter.toEntity(form);
        WikiEntryVersion versionEntity = converter.toVersionEntity(entity);
        versionEntity.setComment(form.getComment() );
        WikiEntryVersion oldVersion = QueryChain.of(versionMapper)
                .where(WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.WIKI_ENTRY_ID.eq(form.getId()))
                .orderBy(WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.VERSION_NUMBER.asc(),
                        WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.VERSION_NUMBER.desc())
                .one();
        versionEntity.setVersionNumber(oldVersion.getVersionNumber()+1);


        boolean b = this.updateById(entity);
        int insert = versionMapper.insert(versionEntity);

        return b&&insert>0;
    }

    @Override
    public WikiEntryDetailDTO getWikiEntryWithVersion(Long id, Integer version) {
        WikiEntryDetailDTO dto = this.getWikiEntryById(id);

        WikiEntryVersion entryVersion = QueryChain.of(versionMapper).where(
                WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.WIKI_ENTRY_ID.eq(id)
                        .and(WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.VERSION_NUMBER.eq(version))).one();

        dto.setComment(entryVersion.getComment());
        dto.setContent(entryVersion.getContent());
        return dto;
    }

    @Override
    public List<WikiEntryVersion> queryWikiEntryVersion(Long id) {

        return QueryChain.of(versionMapper)
                .select(
                        WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.WIKI_ENTRY_ID,
                        WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.VERSION_NUMBER,
                        WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.COMMENT,
                        WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.CREATED_AT
                        )
                .where(WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.WIKI_ENTRY_ID.eq(id))
                .list();
    }

    @Override
    public Boolean forceDeleteWikiEntry(Long id) {
        versionMapper.deleteByCondition(WikiEntryVersionTableDef.WIKI_ENTRY_VERSION.WIKI_ENTRY_ID.eq(id));
        entityTagMapper.deleteByCondition(EntityTagTableDef.ENTITY_TAG.ENTITY_ID.eq(id));
        return this.removeById(id);
    }

    @Override
    public DraftSubmitConflictDTO<WikiEntryDetailDTO> submitDraftAsNewVersion(Long draftId) {
        DraftSubmitConflictDTO<WikiEntryDetailDTO> dto = new DraftSubmitConflictDTO<>();

        Draft draft = draftService.getById(draftId);
        WikiEntryVersion draftVersion = JSON.parseObject(draft.getData(), WikiEntryVersion.class);
        WikiEntryDetailDTO dbVersion = this.getWikiEntryById(draft.getEntityId());
        
        //当内容不同且草稿创建时间在当前提价版本之前时，返回冲突的响应
        if(!draftVersion.getContent().equals(dbVersion.getContent())
                &&dbVersion.getUpdatedAt().isAfter(draftVersion.getCreatedAt())){
            dto.setCurrentVersion(dbVersion);
            dbVersion.setContent(draftVersion.getContent());
            dbVersion.setUpdatedAt(draftVersion.getCreatedAt());
            dto.setCurrentVersion(dbVersion);
            return dto;
        }else{
            WikiEntryForm form = converter.versionToForm(draftVersion);
            this.submitNewEntryVersion(form);
            return dto;
        }
        
    }

}
