package org.rookie.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.rookie.model.bo.CommentBO;

import java.util.List;

@Data
@NoArgsConstructor
public class PageResult<T> {
    private Long total;

    private List<T> records;
}
