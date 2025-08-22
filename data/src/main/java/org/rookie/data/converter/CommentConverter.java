package org.rookie.data.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.rookie.model.bo.CommentBO;
import org.rookie.model.entity.database.Comment;
import org.rookie.model.form.CommentForm;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentConverter {
    CommentConverter INSTANCE = Mappers.getMapper(CommentConverter.class);

    List<CommentBO> toCommentBOList(List<Comment> commentList);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "nickname", source = "user.nickname")
    @Mapping(target = "avatarUrl", source = "user.avatarUrl")
    @Mapping(target = "children", qualifiedByName = "convertChildrenComment")
    CommentBO toCommentBO(Comment comment);


    @Named("convertChildrenComment")
    @Mapping(target = "children",ignore = true)
    CommentBO toCommentBOWithoutChildren(Comment comment);

    Comment formToComment(CommentForm form);

}
