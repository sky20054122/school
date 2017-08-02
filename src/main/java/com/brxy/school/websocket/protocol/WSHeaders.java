/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */

package com.brxy.school.websocket.protocol;


import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.MultiValueMap;

import com.brxy.school.webcoket.WSAction;




/**
 * @author Eric
 *
 */
public class WSHeaders implements MultiValueMap<String, String>, Serializable
{
    private static final long serialVersionUID = -8321657703086086908L;

    private static final String ACTION = "action";

    private static final String REQUEST_ID = "requestId";
    
    private static final String STATUS_CODE = "statusCode";
    
    private static final String FAIL_REASON = "failReason";

    private final Map<String, List<String>> headers;


    public WSHeaders() {
        this(new LinkedCaseInsensitiveMap<List<String>>(8, Locale.ENGLISH),
                false);
    }

    public static WSHeaders readOnlyWSHeaders(WSHeaders headers)
    {
        return new WSHeaders(headers, true);
    }

    private WSHeaders(Map<String, List<String>> headers, boolean readOnly) {
        Assert.notNull(headers, "'headers' must not be null");
        if(readOnly)
        {
            Map<String, List<String>> map = new LinkedCaseInsensitiveMap<List<String>>(
                    headers.size(), Locale.ENGLISH);
            for(Entry<String, List<String>> entry : headers.entrySet())
            {
                List<String> values = Collections.unmodifiableList(entry
                        .getValue());
                map.put(entry.getKey(), values);
            }
            this.headers = Collections.unmodifiableMap(map);
        }
        else
        {
            this.headers = headers;
        }
    }

    public void setAction(WSAction action)
    {
        set(ACTION, action.toString());
    }

    public WSAction getAction()
    {
        String value = getFirst(ACTION);
        return (value != null ? WSAction.valueOf(value)
                : WSAction.UNKNOWN_ACTION);
    }

    public void setRequestId(String requestId)
    {
        set(REQUEST_ID, requestId);
    }

    public String getRequestId()
    {
        return getFirst(REQUEST_ID);
    }
    
    public void setStatusCode(WSStatus statusCode)
    {
        set(STATUS_CODE, statusCode.toString());
    }
    
    public WSStatus getStatusCode()
    {
        return WSStatus.valueOf(getFirst(STATUS_CODE));
    }
    
    public void setFailReason(String failReason)
    {
        set(FAIL_REASON, failReason);
    }
    
    public String getFailReason()
    {
        return getFirst(FAIL_REASON);
    }

    @Override
    public int size()
    {
        return headers.size();
    }

    @Override
    public boolean isEmpty()
    {
        return headers.isEmpty();
    }

    @Override
    public boolean containsKey(Object key)
    {
        return headers.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value)
    {
        return headers.containsValue(value);
    }

    @Override
    public List<String> get(Object key)
    {
        return headers.get(key);
    }

    @Override
    public List<String> put(String key, List<String> value)
    {
        return headers.put(key, value);
    }

    @Override
    public List<String> remove(Object key)
    {
        return headers.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<String>> m)
    {
        headers.putAll(m);
    }

    @Override
    public void clear()
    {
        headers.clear();
    }

    @Override
    public Set<String> keySet()
    {
        return headers.keySet();
    }

    @Override
    public Collection<List<String>> values()
    {
        return headers.values();
    }

    @Override
    public Set<java.util.Map.Entry<String, List<String>>> entrySet()
    {
        return headers.entrySet();
    }

    @Override
    public String getFirst(String headerName)
    {
        List<String> headerValues = headers.get(headerName);
        return headerValues != null ? headerValues.get(0) : null;
    }

    @Override
    public void add(String headerName, String headerValue)
    {
        List<String> headerValues = headers.get(headerName);
        if(headerValues == null)
        {
            headerValues = new LinkedList<String>();
            this.headers.put(headerName, headerValues);
        }
        headerValues.add(headerValue);
    }

    @Override
    public void set(String headerName, String headerValue)
    {
        List<String> headerValues = new LinkedList<String>();
        headerValues.add(headerValue);
        headers.put(headerName, headerValues);
    }

    @Override
    public void setAll(Map<String, String> values)
    {
        for(Entry<String, String> entry : values.entrySet())
        {
            set(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Map<String, String> toSingleValueMap()
    {
        LinkedHashMap<String, String> singleValueMap = new LinkedHashMap<String, String>(
                this.headers.size());
        for(Entry<String, List<String>> entry : headers.entrySet())
        {
            singleValueMap.put(entry.getKey(), entry.getValue().get(0));
        }
        return singleValueMap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "WSHeaders [headers=" + headers + "]";
    }
}
