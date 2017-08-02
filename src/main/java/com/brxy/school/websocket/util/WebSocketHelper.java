/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */

package com.brxy.school.websocket.util;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.brxy.school.common.DeviceStatus;
import com.brxy.school.common.WSPushStatus;
import com.brxy.school.service.domain.DistributedRequestBean;
import com.brxy.school.service.domain.ResponseResult;
import com.brxy.school.util.CommonUtils;
import com.brxy.school.util.json.CustomObjectMapper;
import com.brxy.school.webcoket.ErrorCode;
import com.brxy.school.webcoket.WSAction;
import com.brxy.school.websocket.protocol.WSEntity;
import com.brxy.school.websocket.protocol.WSHeaders;
import com.brxy.school.websocket.protocol.WSStatus;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * @author xiaobing
 * 
 */
public class WebSocketHelper
{
    private static Logger LOGGER = LoggerFactory.getLogger(WebSocketHelper.class);
    
    /**
     * 构造服务器端web socket请求消息
     * 
     * @param <T>
     * @param pushResource
     * @param valueOf
     * @param class1
     * @param requestBody
     * @throws JsonProcessingException 
     */
    public static <T> TextMessage wrapWSRequestMessage(WSAction action,
            String requestId, T requestBody) throws JsonProcessingException
    {
        WSEntity<T> wsEntity = createWsEntity(action, requestId, null, requestBody);
        TextMessage textMessage = createTextMessage(wsEntity);
        return textMessage;
    }
    
    /**
     * 构造服务器端web socket请求消息
     * 
     * @param <T>
     * @param pushResource
     * @param valueOf
     * @param class1
     * @param requestBody
     * @throws JsonProcessingException 
     */
    public static <T> TextMessage wrapWSRequestMessage(WSAction action,
            String requestId, ErrorCode errorCode, T requestBody) throws JsonProcessingException
    {
        WSEntity<T> wsEntity = createWsEntity(action, requestId, errorCode, requestBody);
        TextMessage textMessage = createTextMessage(wsEntity);
        return textMessage;
    }
    
    /**
     * 编码为JSON格式，并生成TextMessage
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static TextMessage createTextMessage(Object object)
            throws JsonProcessingException
    {
        String wsEntityStr = CustomObjectMapper.encodeMessage(object);
        TextMessage textMessage = new TextMessage(wsEntityStr);
        return textMessage;
    }
    
    /**
     * 创建WSEntity
     * @param action
     * @param requestId
     * @param requestBody
     * @return
     */
    public static <T> WSEntity<T> createWsEntity(WSAction action,
            String requestId, ErrorCode errorCode, T requestBody) {
        WSHeaders headers = new WSHeaders();
        headers.setAction(action);
        headers.setRequestId(requestId);
        if(null!=errorCode) {
        	headers.setFailReason(errorCode.name());
        }
        WSEntity<T> wsEntity = new WSEntity<T>(requestBody, headers);
        return wsEntity;
    }
    
    
    
    /**
     * 通用请求推送方法
     * @param deviceUUID
     * @param action
     * @param requestId
     * @param requestBody
     * @param operInfo
     */
    public static <T> WSPushStatus pushRequest(String deviceUUID, WSAction action, String requestId, T requestBody, String promptMsg) {
        WSHeaders headers = new WSHeaders();
        headers.setAction(action);
        headers.setRequestId(requestId);
        return pushRequest(deviceUUID, headers, requestBody, promptMsg);
    }
    
    /**
     * 通用请求推送方法
     * @param deviceUUID
     * @param action
     * @param requestId
     * @param requestBody
     * @param operInfo
     */
    public static <T> WSPushStatus pushRequest(String deviceUUID, WSHeaders headers, T requestBody, String promptMsg) {
        
        // generate request message and send
        WSEntity<T> wsEntity = new WSEntity<T>(requestBody, headers);
        
        DistributedRequestBean<T> requestBean = new DistributedRequestBean<T>(wsEntity, promptMsg);
        return pushRequest(deviceUUID, requestBean);
        
    }
    
    /**
     * WebSocket请求推送核心方法
     * @param deviceUUID
     * @param requestBean
     * @return
     */
    public static <T> WSPushStatus pushRequest(String deviceUUID, DistributedRequestBean<T> requestBean) {
		return null;
//    	DeviceStatus status = ShareTools.getCurrDeviceStatus(deviceUUID);
//    	
//        if(status != DeviceStatus.OFFLINE) //只要设备不离线就执行发布操作
//        {
//            SessionManager sessionManager = SessionManager.getInstance();
//            WebSocketSession session = sessionManager.getSession(deviceUUID);
//            
//            if(null != session)
//            {
//                if(session.isOpen())
//                {
//                    LOGGER.info("Device is online and connected on current server, push the request directly...");
//                    //直接发布
//                    return push(deviceUUID, requestBean.getEntity(), requestBean.getPromptMsg());
//                }
//                else 
//                {
//                    LOGGER.info("Device is online and connected on current server, but the session is closed, will push the request to the distributed queue...");
//                    //session已失效,将其从map中移除
//                    sessionManager.removeLocalDeviceSession(deviceUUID);
//                }
//            }
//            
//            LOGGER.info("Device is online but connected on other server nodes. Notify it to other servers...");
//            
//            //通知其它服务器执行发布操作
//            DeviceOrderPublisher publisher = CommonUtils.getBean(DeviceOrderPublisher.class);
//            publisher.publishDistributedRequest(deviceUUID, requestBean);
//            return WSPushStatus.SEND_DISTRIBUTED;
//            
//		} else // 如果设备离线
//		{
//			LOGGER.warn(
//					"Device (deviceUUID={}) is not noline. Failed to push the request to it.",
//					deviceUUID);
//			return WSPushStatus.SEND_FAILED;
//		}
        
    }
    
    /**
     * 通用推送方法，可以推送请求或者响应，但是必须提供构造好的请求体(WSEntity)或者响应体(WSResponseEntity)
     * 如果可以确保设备session与本地服务器连接（如响应设备过来的请求），可以直接调用此方法
     * 
     * @param deviceUUID
     * @param entity
     * @param promptMsg
     * @return
     */
    public static <T> WSPushStatus push(String deviceUUID,  WSEntity<T> entity, String promptMsg) {
        WSPushStatus isSuccess = WSPushStatus.SEND_FAILED;
        
        LOGGER.debug("Begin to " + promptMsg);
        WebSocketSession session = SessionManager.getInstance().getSession(
                deviceUUID);
        
        // 如果设备已连接
        if(null != session && session.isOpen())
        {
            try
            {
                // generate message and send
                TextMessage message = createTextMessage(entity);
                session.sendMessage(message);
                isSuccess = WSPushStatus.SEND_SUCCESS;
                LOGGER.debug("Succeed to " + promptMsg);
            }
            catch (JsonProcessingException e)
            {
                LOGGER.error("Error happened when transform WSEntity/WSResponseEntity to JSON object", e);
            }
            catch (IOException e)
            {
                LOGGER.error("Error happened when send messages to device. deviceUUID=" + deviceUUID, e);
            }
        }
        else //设备未连接
        {
            LOGGER.warn("Device is not ONLINE currently. Failed to " + promptMsg);
        }
        
        return isSuccess;
    }
    
    /**
     * 通用响应处理
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ResponseResult parseResponse(String deviceUUID,
            WSEntity responseEntity, String promptMsg)
    {
    	ResponseResult responseResult = new ResponseResult();
        LOGGER.debug("Handle the response message of " + promptMsg
                + "responseEntity=" + responseEntity);
        responseResult.setDeviceUUID(deviceUUID);
        WSStatus statusCode = responseEntity.getHeaders().getStatusCode();
        responseResult.setStatusCode(statusCode);
        
        // 成功或者已经存在于终端
        if(statusCode == WSStatus.SUCCESS)
        {
            LOGGER.debug("Succeeded to " + promptMsg);
            if(null!=responseEntity.getData())
            {
            	responseResult.setResult(responseEntity.getData());
            }
        }
        else
        {
            String failReason = responseEntity.getHeaders().getFailReason();
            LOGGER.error("Failed to " + promptMsg + " failReason:" + failReason);
            responseResult.setResult(failReason);
        }
        return responseResult;
    }
}
