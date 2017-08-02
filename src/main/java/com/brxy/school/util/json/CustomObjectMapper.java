/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */
package com.brxy.school.util.json;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Eric
 *
 */
public class CustomObjectMapper
{
 
    private static ObjectMapper mapper = new ObjectMapper();
    
    static {
        mapper.setSerializationInclusion(Include.NON_EMPTY);
    }
    
    public static <T> String encodeMessage(T data) throws JsonProcessingException {
        return mapper.writeValueAsString(data);
    }
    
    public static <T> T decodeMessage(String data, Class<T> mapClass) throws IOException {
        T t = mapper.readValue(data, mapClass);
        return t;
    }
    
    public static <T> T readValue(String content, TypeReference<T> valueTypeRef) throws IOException
    {
        return mapper.readValue(content, valueTypeRef);
    } 
}
