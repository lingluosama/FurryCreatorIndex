package org.rookie.data.service;

import com.mybatisflex.core.service.IService;
import org.rookie.model.entity.database.Draft;
import org.rookie.model.form.WikiEntryForm;

public interface IDraftService extends IService<Draft> {
    
    Draft saveDraft(WikiEntryForm form);
    
}
