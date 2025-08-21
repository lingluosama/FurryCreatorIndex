package org.rookie.config;


import com.mybatisflex.core.datasource.DataSourceShardingStrategy;
import com.mybatisflex.core.util.StringUtil;

import java.lang.reflect.Method;

/**
 * 读写分离设置
 */
public class RWSeparationStrategy implements DataSourceShardingStrategy {
    @Override
    public String doSharding(String currentDataSourceKey
            , Object mapper, Method mapperMethod, Object[] methodArgs) {

        if("other".equals(currentDataSourceKey)){
            return currentDataSourceKey;
        }

        if (StringUtil.startsWithAny(mapperMethod.getName(),
                "insert", "delete", "update")){
            return "writer";
        }

        return "reader";

    }
    
}
