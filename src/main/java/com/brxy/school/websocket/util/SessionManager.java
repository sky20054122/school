
package com.brxy.school.websocket.util;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.brxy.school.common.FirmVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.brxy.school.common.FirmVersion;
import com.brxy.school.service.DeviceService;
import com.brxy.school.util.CommonUtils;
import com.brxy.school.util.SecurityUtil;
import com.brxy.school.webcoket.ErrorCode;
import com.brxy.school.webcoket.WSAction;
import com.brxy.school.webcoket.config.WebSocketConstant;




/**
 * Web Socket Session manager
 * 
 * @author Eric
 * 
 */
public class SessionManager
{
	private static Logger logger = LoggerFactory
            .getLogger(SessionManager.class);

    private static SessionManager sessionManager = new SessionManager();


    private SessionManager() {
    }

    public static SessionManager getInstance()
    {
        return sessionManager;
    }

    /**
     * 新增session
     * 
     * @param session
     * @throws IOException
     */
    public void addSession(WebSocketSession session) throws IOException
    {
        WSocketAttr attr = retrieveDeviceUUID(session);
        
        //检验连接的合法性
        if(checkConnection(session, attr)) {
        	logger.info(
        			"A device successfully CONNECTTED to server. deviceUUID={}, version={}",
        			attr.deviceUUID, attr.version);
        	
        	// 保存连接对应的sssion映射
        	SessionFactory.getInstance().getSessionMap(attr.version).put(attr.deviceUUID, session);
        }
        
    }
    
    /*
     * 检验连接合法性
     */
    private boolean checkConnection(WebSocketSession session, WSocketAttr attr) throws IOException {
//    	if(attr == null)
//        {
//            rejectSession(null, ErrorCode.INVALID_CONN_URL, session);
//            return false;
//        }
//    	DeviceService deviceService = CommonUtils.getBean(DeviceService.class);
//    	
//        // 判断deviceUUID合法性,是否录入数据库
//        boolean isRecorded = deviceService.isDeviceUUIDRecorded(attr.deviceUUID);
//        if(!isRecorded)
//        {
//        	rejectSession(attr.deviceUUID, ErrorCode.DEVICE_NOT_RECORDED, session);
//            return false;
//        }
//        
//        if(attr.accessCode.equals(WSAction.REQUEST_ACTIVE_DTS_DEVICE.name())){
//        	WebSocketSessionHelper.setRestrictedAction(session, WSAction.REQUEST_ACTIVE_DTS_DEVICE);
//        	WebSocketSessionHelper.setRestrictedAction(session, WSAction.OBTAIN_DTS_DEVICE_ACTIVE_CODE);
//        } else if(attr.accessCode.equals(WSAction.DOWNLOAD_DTS_LOCAL_CONFIGURATIONS.name())) {
//        	WebSocketSessionHelper.setRestrictedAction(session, WSAction.DOWNLOAD_DTS_LOCAL_CONFIGURATIONS);
//        } else {
//        	//判断是被是否已激活
//			boolean isRegisted = deviceService.isDeviceRegisted(attr.deviceUUID);
//			if(!isRegisted) {
//				rejectSession(attr.deviceUUID, ErrorCode.DEVICE_NOT_REGISTERED, session);
//	            return false;
//			}
//			// 判断accessCode是否正确
//			boolean isAccessCodeValid = SecurityUtil.validateAccessCode(
//					attr.deviceUUID, attr.accessCode);
//			if (!isAccessCodeValid) {
//				rejectSession(attr.deviceUUID, ErrorCode.INVALID_ACCESS_CODE, session);
//				return false;
//			}
//        }
//        
//        WebSocketSessionHelper.setWebSocketAttr(session, attr);
        return true;
    }
    
	private void rejectSession(String deviceUUID, ErrorCode errorCode, WebSocketSession session) throws IOException
    {
        logger.error("Connection request will be REJECTED. deviceUUID={} ErrorCode={}", deviceUUID, errorCode);
        //This interface is only used in DTS2B device. It is useless from DTS2C
        TextMessage textMsg = WebSocketHelper.wrapWSRequestMessage(WSAction.REJECT_DTS_CONNECTION, CommonUtils.genUUID(), errorCode, null);
        session.sendMessage(textMsg);
        
        session.close(new CloseStatus(CloseStatus.NOT_ACCEPTABLE.getCode(), errorCode.name()));
    }
	
	private FirmVersion	getDeviceSubVersion(String deviceUUID) {
    	DeviceService deviceService = CommonUtils.getBean(DeviceService.class);
    	return deviceService.getDeviceFirmVersionByID(deviceUUID);
	}
	
    /**
     * 移除session
     * 
     * @param session
     * @throws IOException 
     */
    public void removeSession(WebSocketSession session)
    {
        WSocketAttr attr = retrieveDeviceUUID(session);
        if(attr != null)
        {
            logger.info("The connection for device(deviceUUID="
                    + attr.deviceUUID + ") is CLOSED");
            removeLocalDeviceSession(attr.deviceUUID, attr.getVersion());
        }
    }

    /**
     * 移除设备在本服务器的连接
     * @param deviceUUID
     * @param version
     * @return
     */
	public boolean removeLocalDeviceSession(String deviceUUID) {
        FirmVersion version = getDeviceSubVersion(deviceUUID);

		return removeLocalDeviceSession(deviceUUID, version);
	}
	
    /**
     * 移除设备在本服务器的连接
     * @param deviceUUID
     * @param version
     * @return
     */
	public boolean removeLocalDeviceSession(String deviceUUID, FirmVersion version) {
		boolean result = false;
		WebSocketSession rmSession = (WebSocketSession) SessionFactory
				.getInstance().getSessionMap(version).remove(deviceUUID);
		if (rmSession != null) {
			try {
				rmSession.close();
				result = true;
			} catch (IOException e) {
				logger.info("Error happened while close a session", e);
			}
		}
		return result;
	}
	
	/**
     * 在所有服务器断开设备建立的连接
     * 
     * @param session
     * @throws IOException 
     */
    public void removeDeviceSession(String deviceUUID)
    {
        if(!CommonUtils.isEmpty(deviceUUID))
        {
            logger.info("Remove the session of the device(deviceUUID="
                    + deviceUUID + ")");

            FirmVersion version = getDeviceSubVersion(deviceUUID);
        	
//        	if(ShareTools.isDeviceOnline(deviceUUID)) {
//        		if (!removeLocalDeviceSession(deviceUUID)) {
//        			// 发布到其他服务器
//        			PubSubService pubSubService = CommonUtils
//        					.getBean(PubSubService.class);
//        			pubSubService.publishRemoveSessionMessage(deviceUUID, version);
//        		}
//        	}
        }
    }

    /**
     * 获取设备对应的session
     * 
     * @param deviceUUID
     * @return
     */
    public WebSocketSession getSession(String deviceUUID)
    {
        FirmVersion subVersion = getDeviceSubVersion(deviceUUID);
    	logger.debug("The deviceSubVersion of device(deviceUUID={}) is {}", deviceUUID, subVersion);
        return (WebSocketSession)SessionFactory.getInstance().getSessionMap(subVersion).get(deviceUUID);
    }

    /**
     * 从session中提取deviceUUID
     * 
     * @param session
     * @return
     * @throws IOException
     */
    public static WSocketAttr retrieveDeviceUUID(WebSocketSession session)
    {
		return null;
//        String uri = session.getUri().toString();
//        
//        WSocketAttr attr = null;
//        Pattern uriPattern = Pattern
//                .compile("/"
//                        + ShareTools.getServerParameter(ServerConfiguration.SERVER_PROJECT_NAME)
//                        + WebSocketConstant.CONNECTION_HANDLER_URL_PREFIX
//                        + "(\\w+)" + "/" + "(\\w+)" + "/(\\w+)");
////                        + "(v\\d*.*\\w*)" + "/" + "(\\w+)" + "/(\\w+)");
//        Matcher matcher = uriPattern.matcher(uri);
//        if(matcher.matches())
//        {
//            String versionStr = matcher.group(1).trim();
//            String deviceUUIDStr = matcher.group(2).trim();
//            String accessCodeStr = matcher.group(3).trim();
//            if(!CommonUtils.isEmpty(deviceUUIDStr)
//                    && !CommonUtils.isEmpty(deviceUUIDStr)
//                    && !CommonUtils.isEmpty(accessCodeStr))
//            {
//            	try
//            	{
//					switch (versionStr) {
//					case WebSocketConstant.DTS2B:
//						attr = new WSocketAttr(firmVersion.DTSK3,
//								deviceUUIDStr, accessCodeStr);
//						break;
//					case WebSocketConstant.DTS2C:
//						attr = new WSocketAttr(firmVersion.DTSK3A,
//								deviceUUIDStr, accessCodeStr);
//						break;
//					default:
//						attr = new WSocketAttr(
//								firmVersion.valueOf(versionStr),
//								deviceUUIDStr, accessCodeStr);
//
//					}
//            	}
//            	catch(Exception e)
//            	{
//            		logger.error("Invalid device version!");
//            	}
//            }
//        }
//        else
//        {
//        	logger.debug("ConnectionURL is not valid! url={}", uri);
//        }
//        return attr;
    }

    public static class WSocketAttr
    {
        private FirmVersion version;

        private String deviceUUID;

        private String accessCode;
        
        public WSocketAttr(FirmVersion version, String deviceUUID, String accessCode) {
            this.version = version;
            this.deviceUUID = deviceUUID;
            this.accessCode = accessCode;
        }

		public FirmVersion getVersion() {
			return version;
		}

		public void setVersion(FirmVersion version) {
			this.version = version;
		}

		public String getDeviceUUID() {
			return deviceUUID;
		}

		public void setDeviceUUID(String deviceUUID) {
			this.deviceUUID = deviceUUID;
		}

		public String getAccessCode() {
			return accessCode;
		}

		public void setAccessCode(String accessCode) {
			this.accessCode = accessCode;
		}
        
    }
    
}
