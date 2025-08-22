package org.rookie.model.query;

import lombok.Data;

@Data
public class BasePager {
    private Integer pageSize=10;
    private Integer pageNumber=1;
}
