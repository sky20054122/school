/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */
package com.brxy.school.webcoket.service;

import com.brxy.school.service.domain.ResponseResult;

/**
 * @author Eric
 *
 */
public interface WSResponseService
{
//    /**
//     * 处理成功响应
//     * @param requestId
//     * @param obj
//     */
//    public void doSuccess(String requestId, Object obj);
//
//    /**
//     * 处理失败响应
//     * @param requestId
//     */
//    public void doFail(String requestId, Object failReason);
	
	/**
	 * 设置响应结果
	 * @param requestId
	 * @param resultObj 
	 */
	public void setResult(String requestId, ResponseResult responseResult);

    /**
     * 获取响应结果
     * @param requestId
     * @return 成功则返回结果，失败返回null
     */
    public Object getResult(String requestId);
    
    /**
     * 缓存请求信息
     * @param requestId
     * @param obj
     */
    public <T> void setInfo(String requestId, T obj);

    /**
     * 获取缓存的请求信息
     * @param requestId
     * @return
     */
	public <T> T getInfo(String requestId, Class<T> classz);

}
