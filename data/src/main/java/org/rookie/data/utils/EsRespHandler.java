package org.rookie.data.utils;


import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class EsRespHandler {
    private final ObjectMapper objectMapper;

    public <T> List<T> extractHistFromAfterFiled(SearchResponse<Map> response, Class<T> targetType){
        return response.hits().hits().stream()
                .map(Hit::source)
                .filter(Objects::nonNull)
                .map(source -> {

                    Map<String,Object> afterData= (Map<String, Object>) source.get("after");
                    if(afterData==null){
                        log.warn("Elastic 响应存在空数据段:{}",source);
                        return null;
                    }
                    return convertValue(afterData,targetType);
                })
                .filter(Objects::nonNull)
                .toList();

    }

    public long getTotalHits(SearchResponse<Map> response) {
        if (response.hits().total() != null) {
            return response.hits().total().value();
        }
        return 0;
    }

    public <T> T convertValue(Map<String,Object> source, Class<T> targetType){
        try {
            return objectMapper.convertValue(source, targetType);
        }catch (Exception e){
            log.error("Elastic Map映射失败:{}:{}",targetType.getSimpleName(),e.getMessage());
            return null;
        }
    }

}
