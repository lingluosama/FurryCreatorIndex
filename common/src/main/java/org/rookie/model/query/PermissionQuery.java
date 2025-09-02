package org.rookie.model.query;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionQuery extends BasePager{
    String keyword;
}
