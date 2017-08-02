
package com.brxy.school.common;


public interface SpringUtil
{
    public Object getBean(String beanName);

    public <T> T getBean(Class<T> classz);

    public Object getApplicationContext();
}
