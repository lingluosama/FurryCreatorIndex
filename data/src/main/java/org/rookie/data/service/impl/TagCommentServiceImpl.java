package org.rookie.data.service.impl;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.rookie.data.converter.CommentConverter;
import org.rookie.data.converter.TagConverter;
import org.rookie.data.mapper.CommentMapper;
import org.rookie.data.mapper.EntityTagMapper;
import org.rookie.data.mapper.TagMapper;
import org.rookie.data.service.ITagCommentService;
import org.rookie.model.bo.CommentBO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.entity.database.Comment;
import org.rookie.model.entity.database.EntityTag;
import org.rookie.model.entity.database.Tag;
import org.rookie.model.entity.database.table.EntityTagTableDef;
import org.rookie.model.entity.database.table.TagTableDef;
import org.rookie.model.form.CommentForm;
import org.rookie.model.form.TagForm;
import org.rookie.model.query.EntityCommentPageQuery;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.rookie.model.entity.database.table.CommentTableDef.COMMENT;

@Service
@RequiredArgsConstructor
public class TagCommentServiceImpl implements ITagCommentService {

    private final CommentMapper commentMapper;

    private final TagMapper tagMapper;

    private final EntityTagMapper entityTagMapper;

    private final CommentConverter commentConverter;

    private final TagConverter tagConverter;

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
    public Void overwriteEntityTags(Long entityId, String entityType, List<Long> newTags) {
        List<Long> oldTagIds = QueryChain.of(entityTagMapper)
                .select(EntityTagTableDef.ENTITY_TAG.TAG_ID)
                .where(EntityTagTableDef.ENTITY_TAG.ENTITY_ID.eq(entityId))
                .list().stream().map(EntityTag::getTagId).toList();

        Set<Long> oldSet=new HashSet<>(oldTagIds);
        Set<Long> newSet=new HashSet<>(newTags);

        Set<Long> toDelete=new HashSet<>(oldSet);
        toDelete.removeAll(newSet);

        Set<Long> toAdd=new HashSet<>(newTags);
        toAdd.removeAll(oldSet);


        if(!toDelete.isEmpty())entityTagMapper.deleteByCondition(
                EntityTagTableDef.ENTITY_TAG.ENTITY_TYPE.eq(entityType)
                        .and(EntityTagTableDef.ENTITY_TAG.TAG_ID.in(toDelete))
                        .and(EntityTagTableDef.ENTITY_TAG.ENTITY_ID.eq(entityId)));
        if(!toAdd.isEmpty()){
            List<EntityTag> entityTags = toAdd.stream().map(tagId -> new EntityTag(entityId, entityType, tagId)).toList();
            entityTagMapper.insertBatch(entityTags);
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
}
