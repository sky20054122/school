package com.brxy.school.webcoket.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.brxy.school.common.FirmVersion;
import com.brxy.school.exceptions.SystemException;
import com.brxy.school.util.CommonUtils;
import com.brxy.school.webcoket.config.WebSocketConstant;
import com.brxy.school.websocket.util.SessionManager;



public class ConnectionHandler extends TextWebSocketHandler {
	private Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);

	/**
	 * 当连接建立时触发的操作
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		// 保存设备和session的对应关系
		try {
			SessionManager.getInstance().addSession(session);
		} catch (IOException e) {
			logger.error("Error happened while adding a session.", e);
		}
		
	}

	/**
	 * 当连接断开时触发
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) {
		String deviceUUID = (String) session.getAttributes().get(
				WebSocketConstant.DEVICE_IDENTITY_LABEL);
		
		try {
			// 取消设备和session的对应关系
			SessionManager.getInstance().removeSession(session);
		} catch(SystemException se) {
			//logger.error(se.getCustomMessage());
		} catch(Exception e) {
			logger.error("Error happened while closing the websocket connection.", e);
		}
		
		// 设置 设备状态为离线
//		if (!CommonUtils.isEmpty(deviceUUID)) {
//			DeviceDetailedInfoService infoService = CommonUtils
//					.getBean(DeviceDetailedInfoService.class);
//			infoService.setDeviceStatus(deviceUUID, DeviceStatus.OFFLINE);
//		} else {
//			if (logger.isWarnEnabled()) {
//				logger.warn("A closed session not contain the device uuid");
//			}
//		}
	}

	/**
	 * 处理文本消息
	 */
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		//从session中获取设备UUID及子版本号
		String deviceUUID = (String) session.getAttributes().get(
				WebSocketConstant.DEVICE_IDENTITY_LABEL);
		FirmVersion deviceSubVersion = (FirmVersion) session
				.getAttributes().get(WebSocketConstant.DEVICE_VERSION_LABEL);
		if (CommonUtils.isEmpty(deviceUUID)
				|| CommonUtils.isEmpty(deviceSubVersion)) {
			logger.warn("This session is not valid. Discarded the message. session="
					+ session + ", message=" + message);
			return;
		}

		try {
			// 处理请求
			MessageHandlerBroker.handleRequest(session, deviceUUID, deviceSubVersion, message);
		} catch (Exception e) {
			logger.error(
					"Error happened while handel the message from device. deviceUUID={}, deviceVersion={}, Message:{}",
					deviceUUID, deviceSubVersion, message.getPayload(), e);
		}
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session,
			BinaryMessage message) {
		logger.debug("Receive a BinaryMessage!");
	}

	@Override
	protected void handlePongMessage(WebSocketSession session,
			PongMessage message) throws Exception {
		logger.debug("reveive a pong message!");
	}
}