package org.rookie.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CommentBO {
    private Long id;

    private String username;

    private String nickname;

    private String avatarUrl;

    private String content;

    private String status;

    private List<CommentBO> children;

}
