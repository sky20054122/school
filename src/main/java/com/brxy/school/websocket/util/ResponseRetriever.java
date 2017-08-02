/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */

package com.brxy.school.websocket.util;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brxy.school.common.WSPushStatus;
import com.brxy.school.service.domain.ResponseResult;


/**
 * @author Eric
 * 
 */
public class ResponseRetriever
{
    private static Logger logger = LoggerFactory.getLogger(ResponseRetriever.class);
    private static final Long TIME_OUT_SECONDS = 10L;
    
    private static ExecutorService executorService = Executors
            .newCachedThreadPool();

    /**
     * 从缓存获取实时响应信息
     * @param isNotified
     * @param requestId
     * @return
     */
    public static ResponseResult retrieveResult(WSPushStatus isNotified, String requestId)
    {
    	ResponseResult responseResult = null;
        FutureTask<ResponseResult> futureTask = new FutureTask<ResponseResult>(new ResponseRetrieverTask(isNotified,
                requestId));
        executorService.submit(futureTask);
        try
        {
            responseResult = futureTask.get(TIME_OUT_SECONDS, TimeUnit.SECONDS);
            
        }
        catch (InterruptedException | ExecutionException | TimeoutException e)
        {
            logger.error("Error happened while try to get the response result from cache", e);
        }
        return responseResult;
    }
    
}
