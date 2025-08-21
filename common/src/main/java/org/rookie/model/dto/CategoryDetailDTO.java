package org.rookie.model.dto;

import com.mybatisflex.annotation.RelationManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rookie.model.entity.database.WikiCategory;

import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDetailDTO {
    Long id;
    String name;
    String description;

    CategoryDetailDTO parent;
    List<CategoryDetailDTO> children;

}
