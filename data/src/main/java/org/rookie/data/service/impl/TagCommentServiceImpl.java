package org.rookie.data.service.impl;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.rookie.data.converter.CommentConverter;
import org.rookie.data.converter.TagConverter;
import org.rookie.data.mapper.CommentMapper;
import org.rookie.data.mapper.EntityTagMapper;
import org.rookie.data.mapper.TagMapper;
import org.rookie.data.service.TagCommentService;
import org.rookie.exception.BusinessException;
import org.rookie.model.bo.CommentBO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.entity.database.Comment;
import org.rookie.model.entity.database.EntityTag;
import org.rookie.model.entity.database.Tag;
import org.rookie.model.entity.database.table.EntityTagTableDef;
import org.rookie.model.entity.database.table.TagTableDef;
import org.rookie.model.form.CommentForm;
import org.rookie.model.form.EntityTagsForm;
import org.rookie.model.form.TagForm;
import org.rookie.model.query.EntityCommentPageQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.rookie.model.entity.database.table.CommentTableDef.COMMENT;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagCommentServiceImpl implements TagCommentService {

    private final CommentMapper commentMapper;

    private final TagMapper tagMapper;

    private final EntityTagMapper entityTagMapper;

    private final CommentConverter commentConverter;

    private final TagConverter tagConverter;

    private final ElasticsearchClient esClient;

    @Override
    public Tag createTag(TagForm form) {
        Tag tag = tagConverter.toTag(form);
        tagMapper.insert(tag);
        return tag;
    }

    @Override
    public Boolean deleteTag(Long tagId) {
        return tagMapper.deleteById(tagId)>0;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagMapper.selectAll();
    }

    @Override
    public List<Tag> getTagsByEntityId(Long entityId, String entityType) {
        QueryWrapper subQuery= new QueryWrapper()
                .select(EntityTagTableDef.ENTITY_TAG.TAG_ID)
                .from(EntityTagTableDef.ENTITY_TAG)
                .where(EntityTagTableDef.ENTITY_TAG.ENTITY_ID.eq(entityId)
                        .and(EntityTagTableDef.ENTITY_TAG.ENTITY_TYPE.eq(entityType)));

        return QueryChain.of(tagMapper).where(TagTableDef.TAG.ID.in(subQuery)).list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Void overwriteEntityTags(EntityTagsForm form) {
        List<Long> oldTagIds = QueryChain.of(entityTagMapper)
                .select(EntityTagTableDef.ENTITY_TAG.TAG_ID)
                .where(EntityTagTableDef.ENTITY_TAG.ENTITY_ID.eq(form.getEntityId()))
                .list().stream().map(EntityTag::getTagId).toList();

        Set<Long> oldSet=new HashSet<>(oldTagIds);
        Set<Long> newSet=new HashSet<>(form.getTagIds());

        Set<Long> toDelete=new HashSet<>(oldSet);
        toDelete.removeAll(newSet);

        Set<Long> toAdd=new HashSet<>(form.getTagIds());
        toAdd.removeAll(oldSet);


        if(!toDelete.isEmpty())entityTagMapper.deleteByCondition(
                EntityTagTableDef.ENTITY_TAG.ENTITY_TYPE.eq(form.getEntityType())
                        .and(EntityTagTableDef.ENTITY_TAG.TAG_ID.in(toDelete))
                        .and(EntityTagTableDef.ENTITY_TAG.ENTITY_ID.eq(form.getEntityId())));
        if(!toAdd.isEmpty()){
            List<EntityTag> entityTags = toAdd.stream().map(tagId -> new EntityTag(form.getEntityId(), form.getEntityType(), tagId)).toList();
            entityTagMapper.insertBatch(entityTags);
        }

        List<String> tagNameList = tagMapper.selectListByIds(form.getTagIds()).stream().map(Tag::getName).toList();

        if(!updateEsEntityTag(tagNameList, form.getEntityType(), form.getEntityId())){
            log.error("更新标签事务已经回滚");
            throw new BusinessException(HttpStatus.SC_BAD_REQUEST,"es更新失败");
        }


        return null;
    }

    @Override
    public Comment createComment(CommentForm form) {
        Comment comment = commentConverter.formToComment(form);
        commentMapper.insert(comment);
        return comment;
    }

    @Override
    public PageResult<CommentBO> getCommentsByEntityId(EntityCommentPageQuery query) {

        Page<Comment> page = new Page<>(query.getPageNumber(), query.getPageSize());

        QueryChain.of(commentMapper)
                .where(COMMENT.ENTITY_ID.eq(query.getEntityId()))
                .where(COMMENT.ENTITY_TYPE.eq(query.getEntityType()))
                .withRelations()
                .page(page);

        PageResult<CommentBO> dto = new PageResult<>();
        dto.setTotal(page.getTotalRow());
        dto.setRecords(commentConverter.toCommentBOList(page.getRecords()));

        return dto;
    }

    @Override
    public Boolean deleteComment(Long commentId) {
        return commentMapper.deleteById(commentId)>0;
    }

    private Boolean updateEsEntityTag(List<String> tags,String entityType,Long entityId){

        try {
            String indexName = "mysql-wiki-v3.testdb."+entityType;

            UpdateRequest<Map,Map> updateRequest=UpdateRequest.of(u->u
                    .index(indexName)
                    .id(entityId.toString())
                    .doc(createTagsUpdateDoc(tags))
            );
            UpdateResponse<Map> response = esClient.update(updateRequest, Map.class);

            return response.result()== Result.Updated;

        }catch (Exception e){
            log.error("未能同步标签到es:{}",e.getMessage());
            return false;
        }


    }

    private Map<String, Object> createTagsUpdateDoc(List<String> tags) {
        Map<String, Object> updateDoc = new HashMap<>();
        Map<String, Object> afterUpdate = new HashMap<>();

        // 直接设置 tags 数组
        afterUpdate.put("tags", tags);
        updateDoc.put("after", afterUpdate);

        return updateDoc;
    }

}
