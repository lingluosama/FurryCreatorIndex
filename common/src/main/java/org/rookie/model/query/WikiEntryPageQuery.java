package org.rookie.model.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WikiEntryPageQuery extends BasePager{
    String keyword;

    String status;

    Long categoryId;

}
