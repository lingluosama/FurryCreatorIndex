package org.rookie.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.rookie.model.bo.WikiEntryBO;

@Data
@NoArgsConstructor
public  class DraftSubmitConflictDTO<T> {
    T currentVersion;
    
    T userVersion;
}
