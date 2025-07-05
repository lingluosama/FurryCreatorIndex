package org.rookie.config;

import com.mybatisflex.core.datasource.DataSourceShardingStrategy;
import com.mybatisflex.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


public class RandomReadDataSourceStrategy implements DataSourceShardingStrategy {
    
    @Override
    public String doSharding(String currentDataSourceKey,Object mapper, Method method, Object[] methArgs) {
        
        if("other".equals(currentDataSourceKey)){
            return currentDataSourceKey;
        }
        if (StringUtil.startsWithAny(method.getName(),
                "insert", "delete", "update")){
            return "reader";
        }
            
        if(Math.random()<0.5)return "writer";
        return "reader";
        
    }
    


}
