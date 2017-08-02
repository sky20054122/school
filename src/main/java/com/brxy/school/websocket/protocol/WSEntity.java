/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */

package com.brxy.school.websocket.protocol;


import java.io.Serializable;

import org.springframework.util.MultiValueMap;


/**
 * 定义Web Socket请求或者响应体，包括消息头和消息体
 * 
 * @author Eric
 * 
 */
public class WSEntity<T> implements Serializable
{
    private static final long serialVersionUID = 7893361816056573070L;

    // 消息头
    private final WSHeaders headers;

    // 消息体
    private final T data;


    protected WSEntity() {
        this(null, null);
    }

    public WSEntity(T data) {
        this(data, null);
    }

    public WSEntity(MultiValueMap<String, String> headers) {
        this(null, headers);
    }

    public WSEntity(T data, MultiValueMap<String, String> headers) {
        this.data = data;
        WSHeaders tempHeaders = new WSHeaders();
        if(headers != null)
        {
            tempHeaders.putAll(headers);
        }
        this.headers = WSHeaders.readOnlyWSHeaders(tempHeaders);
    }

    public WSHeaders getHeaders()
    {
        return this.headers;
    }

    public T getData()
    {
        return this.data;
    }

    public boolean hasData()
    {
        return (this.data != null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "WSEntity [headers=" + headers + ", data=" + data + "]";
    }
}
