/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */

package com.brxy.school.websocket.protocol;


import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.MultiValueMap;


/**
 * @author Eric
 * 
 */
public class CustomedMap<K, V> implements MultiValueMap<K, V>
{
    private Map<K, List<V>> centralMap;

    
    public CustomedMap() {
        this(new LinkedCaseInsensitiveMap<List<V>>(8, Locale.ENGLISH));
    }

    public CustomedMap(Map<K, List<V>> map) {
        this.centralMap = map;
    }

    public CustomedMap(
            LinkedCaseInsensitiveMap<List<V>> linkedCaseInsensitiveMap) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public int size()
    {
        return centralMap.size();
    }

    @Override
    public boolean isEmpty()
    {
        return centralMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key)
    {
        return centralMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value)
    {
        return centralMap.containsValue(value);
    }

    @Override
    public List<V> get(Object key)
    {
        return centralMap.get(key);
    }

    @Override
    public List<V> put(K key, List<V> value)
    {
        return centralMap.put(key, value);
    }

    @Override
    public List<V> remove(Object key)
    {
        return centralMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends List<V>> m)
    {
        centralMap.putAll(m);
    }

    @Override
    public void clear()
    {
        centralMap.clear();
    }

    @Override
    public Set<K> keySet()
    {
        return centralMap.keySet();
    }

    @Override
    public Collection<List<V>> values()
    {
        return centralMap.values();
    }

    @Override
    public Set<java.util.Map.Entry<K, List<V>>> entrySet()
    {
        return centralMap.entrySet();
    }

    @Override
    public V getFirst(K headerName)
    {
        List<V> headerValues = centralMap.get(headerName);
        return headerValues != null ? headerValues.get(0) : null;
    }

    @Override
    public void add(K headerName, V headerValue)
    {
        List<V> headerValues = centralMap.get(headerName);
        if(headerValues == null)
        {
            headerValues = new LinkedList<V>();
            this.centralMap.put(headerName, headerValues);
        }
        headerValues.add(headerValue);
    }

    @Override
    public void set(K headerName, V headerValue)
    {
        List<V> headerValues = new LinkedList<V>();
        headerValues.add(headerValue);
        centralMap.put(headerName, headerValues);
    }

    @Override
    public void setAll(Map<K, V> values)
    {
        for(Entry<K, V> entry : values.entrySet())
        {
            set(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Map<K, V> toSingleValueMap()
    {
        LinkedHashMap<K, V> singleValueMap = new LinkedHashMap<K, V>(
                this.centralMap.size());
        for(Entry<K, List<V>> entry : centralMap.entrySet())
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
        return "WSHeaders [headers=" + centralMap + "]";
    }
}
