/**
 * 
 */
package com.brxy.school.websocket.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import com.brxy.school.webcoket.WSAction;
import com.brxy.school.webcoket.config.WebSocketConstant;
import com.brxy.school.websocket.util.SessionManager.WSocketAttr;



/**
 * @author Eric
 *
 */
public class WebSocketSessionHelper {
	
	/**
	 * 设置在给定的session内受限制的操作
	 * @param session
	 * @param action
	 */
	@SuppressWarnings("unchecked")
	public static void setRestrictedAction(WebSocketSession session, WSAction action) {
		if(null != session) {
			Map<String, Object> sesstionAttr = session.getAttributes();
			
			Set<WSAction> rstActions = (Set<WSAction>)sesstionAttr.get(WebSocketConstant.DEVICE_RESTRICTED_ACTION);
			if(rstActions == null) {
				rstActions = new HashSet<WSAction>();
			}
			rstActions.add(action);
			sesstionAttr.put(WebSocketConstant.DEVICE_RESTRICTED_ACTION, rstActions);
		}
	}
	
	/**
	 * 获取在指定session内受限制的操作
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<WSAction> getRestricatedAction(WebSocketSession session) {
		if(session != null) {
			return (Set<WSAction>)session.getAttributes().get(WebSocketConstant.DEVICE_RESTRICTED_ACTION);
		}
		return null;
	}
	
	/**
	 * 设置WebSocket属性
	 * @param session
	 * @param attr
	 */
	public static void setWebSocketAttr(WebSocketSession session, WSocketAttr attr) {
		if(null != attr && null != session) {
			 session.getAttributes().put(WebSocketConstant.DEVICE_IDENTITY_LABEL,
		                attr.getDeviceUUID());
		        session.getAttributes().put(WebSocketConstant.DEVICE_VERSION_LABEL,
		                attr.getVersion());
		        session.getAttributes().put(WebSocketConstant.ACCESS_CODE, attr.getAccessCode());
		}
	}

}
