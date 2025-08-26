package org.rookie.data.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.rookie.data.converter.WikiEntryConverter;
import org.rookie.data.mapper.WikiEntryMapper;
import org.rookie.data.mapper.WikiEntryVersionMapper;
import org.rookie.data.service.IWikiEntryService;
import org.rookie.exception.BusinessException;
import org.rookie.model.bo.WikiEntryBO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.dto.WikiEntryDetailDTO;
import org.rookie.model.entity.database.WikiEntry;
import org.rookie.model.entity.database.WikiEntryVersion;
import org.rookie.model.entity.database.table.WikiEntryVersionTableDef;
import org.rookie.model.entity.elastic.WikiEntryEs;
import org.rookie.model.form.WikiEntryForm;
import org.rookie.model.query.WikiEntryPageQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WikiEntryServiceImpl extends ServiceImpl<WikiEntryMapper, WikiEntry> implements IWikiEntryService {

    private final WikiEntryConverter converter;

    private final WikiEntryVersionMapper versionMapper;

    private final ElasticsearchClient esClient;

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
        int from=(query.getPageNumber()-1)-query.getPageSize();
        int size=query.getPageSize();

        SearchRequest request = SearchRequest.of(s -> s
                .index("debezium.testdb.wiki_entry")
                .from(from)
                .size(size)
                .query(q->q.bool(b->{
                    b.must(m->m.multiMatch(mm->
                            mm.query(query.getKeyword())
                                    .fields("title","content")
                    ));

                    if(query.getCategoryId()!=null){
                        b.filter(f->f.term(t->t.field("category_id")
                                .value(String.valueOf(query.getCategoryId()))));
                    }
                    return b;
                }))
        );

        try {
            SearchResponse<WikiEntryEs> response = esClient.search(request, WikiEntryEs.class);
            List<WikiEntryEs> wikiEntryEs = response.hits().hits().stream().map(Hit::source).toList();
            List<WikiEntryBO> list = converter.toBOList(wikiEntryEs);

            PageResult<WikiEntryBO> pageResult = new PageResult<>();
            if (response.hits().total() != null) {
                pageResult.setTotal(response.hits().total().value());
            }
            pageResult.setRecords(list);
            return pageResult;
        }catch (IOException e){
            throw new BusinessException(HttpStatus.SC_BAD_REQUEST,"查询条目失败",e);
        }

    }

    @Override
    public WikiEntryDetailDTO getWikiEntryById(Long id) {
        WikiEntry wikiEntry = this.getMapper().selectOneWithRelationsById(id);
        return converter.toDetailDTO(wikiEntry);
    }

    @Override
    public Boolean deleteWikiEntry(Long id) {
        return this.removeById(id);
    }

    @Override
    public Boolean submitNewEntryVersion(WikiEntryForm form) {
        WikiEntry entity = converter.toEntity(form);
        WikiEntryVersion versionEntity = converter.toVersionEntity(entity);

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
}
