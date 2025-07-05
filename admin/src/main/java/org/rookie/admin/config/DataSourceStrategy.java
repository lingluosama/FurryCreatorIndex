package org.rookie.admin.config;

import com.mybatisflex.core.datasource.DataSourceShardingStrategy;import com.mybatisflex.core.util.StringUtil;import java.lang.reflect.Method;



public class DataSourceStrategy implements DataSourceShardingStrategy {
    
    @Override
    public String doSharding(String currentDataSourceKey,Object mapper, Method method, Object[] methArgs) {
        if("other".equals(currentDataSourceKey)){
            return currentDataSourceKey;
        }
        
        if(StringUtil.startsWithAny(method.getName(), "insert", "delete","update")){
            return  "writer";
            
        }
        return "reader";
        
    }
    


}
