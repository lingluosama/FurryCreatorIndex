package org.rookie.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.rookie.model.entity.database.CreatorTable;
import org.rookie.user.mapper.CreatorMapper;
import org.rookie.user.service.ICreatorService;
import org.springframework.stereotype.Component;

@Component
public class CreatorServiceImpl extends ServiceImpl<CreatorMapper, CreatorTable> implements ICreatorService{
    
    
}
