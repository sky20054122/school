/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */
package com.brxy.school.webcoket.handler;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.brxy.school.common.FirmVersion;
import com.brxy.school.util.CommonUtils;
import com.brxy.school.util.json.CustomObjectMapper;
import com.brxy.school.webcoket.WSAction;
import com.brxy.school.websocket.protocol.WSEntity;
import com.brxy.school.websocket.protocol.WSHeaders;
import com.brxy.school.websocket.util.WebSocketSessionHelper;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * WebSocket请求处理和分发类
 * @author Eric
 *
 */
public class MessageHandlerBroker {
	private static Logger logger = LoggerFactory
			.getLogger(MessageHandlerBroker.class);

	/**
	 * 预处理消息及分发到对应的类处理
	 * 
	 * @param deviceUUID
	 * @param deviceVersionStr
	 * @param message
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void handleRequest(WebSocketSession session, String deviceUUID,
			FirmVersion deviceVersion,	TextMessage message) throws IOException {
		// 解码消息
		WSEntity entity = CustomObjectMapper.decodeMessage(
				message.getPayload(), WSEntity.class);

		WSHeaders headers = entity.getHeaders();
		WSAction action = headers.getAction(); // 命令
		String requestId = headers.getRequestId(); // 请求ID

		logger.debug(
				"Received message from deviceUUID={}, deviceVersion={}, message={}",
				deviceUUID, deviceVersion, message.getPayload());

		// 校验，不符合格式要求直接丢弃
		if (CommonUtils.isEmpty(action) || CommonUtils.isEmpty(requestId)) {
			logger.error("Message format error! action and requestId should not be empty. Discarded.");
			return;
		} 

		// 请求的动作受限制
		Set<WSAction> restrictedAction = WebSocketSessionHelper.getRestricatedAction(session);
		if(restrictedAction!=null && !restrictedAction.contains(action)) {
			logger.error("Invalid request! This connection can only do action {}, but request action is {}",
					restrictedAction.toArray().toString(), action);
			return;
		}
		
//		switch (action) {
//		// ####################################REQUEST############################################
//		// 心跳检测
//		case KEEP_DTS_HEARTBEAT:
//			DeviceRequestHandler.handleHeartbeat(deviceUUID, entity);
//			break;
//		// 报告状态
//		case REPORT_DTS_DEVICE_STATUS:
//			entity = CustomObjectMapper.readValue(message.getPayload(),
//					new TypeReference<WSEntity<List<DeviceStatusEntity>>>() {
//					});
//			DeviceRequestHandler.handleStatusReport(deviceUUID, deviceVersion,
//					entity);
//			break;
//		// 报告设备信息
//		case REPORT_DTS_DEVICE_RUNTIME_INFO:
//			entity = CustomObjectMapper
//					.readValue(
//							message.getPayload(),
//							new TypeReference<WSEntity<List<DeviceRuntimeInfoEntity>>>() {
//							});
//			DeviceRequestHandler.handleRuntimeInfoReport(deviceUUID, entity);
//			break;
//		// 激活设备
//		case REQUEST_ACTIVE_DTS_DEVICE:
//			DeviceRequestHandler.handleDeviceActivate(deviceUUID, entity);
//			break;
//		// 推送配置
//		case CONFIG_DTS_DEVICE_PARAMETERS:
//			ConfigResponseHandler.handlePush(deviceUUID, entity);
//			break;
//		// 同步DTS配置
//		case REQUEST_SYNC_DTS_ALL_PARAMETERS:
//			ConfigurationHandler.handleConfigSyncRequest(deviceUUID,
//					deviceVersion, entity);
//			break;
//		// 同步开机时间列表
//		case REQUEST_SYNC_DTS_BOOT_SCHEDULE:
//			ConfigurationHandler.handleBootScheduleSyncRequest(deviceUUID,
//					deviceVersion, entity);
//			break;
//		// 同步RFID权限
//		case REQUEST_SYNC_DTS_ALL_RFID_PERMISSION:
//			OneCardRequestHandler.handleRfidPermSyncRequest(deviceUUID,
//					deviceVersion, entity);
//			break;
//		// 上报RFID刷卡考勤
//		case REPORT_DTS_RFID_CHECK_ON_DATA:
//			TypeReference<WSEntity<List<RfidSwipedReportEntity>>> trRsie = new TypeReference<WSEntity<List<RfidSwipedReportEntity>>>() {
//			};
//			entity = CustomObjectMapper.readValue(message.getPayload(), trRsie);
//			OneCardRequestHandler.handleSwipeCardReport(deviceUUID,
//					deviceVersion, entity);
//			break;
//		// 上传DTS2B日志
//		case UPLOAD_DTS_DEVICE_LOG:
//			entity = CustomObjectMapper.readValue(message.getPayload(),
//					new TypeReference<WSEntity<DeviceLogEntity>>() {
//					});
//			LogRequestHandler.handleLogUpload(deviceUUID, entity);
//			break;
//		// 上传DTS2B本地配置
//		case UPLOAD_DTS_LOCAL_CONFIGURATIONS:
//			TypeReference<WSEntity<DeviceLocalConfigEntity>> trDtsConf = new TypeReference<WSEntity<DeviceLocalConfigEntity>>() {
//			};
//			entity = CustomObjectMapper.readValue(message.getPayload(),
//					trDtsConf);
//			DeviceRequestHandler.handleDtsLocalConfigUpload(deviceUUID, entity);
//			break;
//		// 下载DTS2B本地配置
//		case DOWNLOAD_DTS_LOCAL_CONFIGURATIONS:
//			trDtsConf = new TypeReference<WSEntity<DeviceLocalConfigEntity>>() {
//			};
//			entity = CustomObjectMapper.readValue(message.getPayload(),
//					trDtsConf);
//			DeviceRequestHandler.handleDtsLocalConfigDownload(deviceUUID,
//					entity);
//			break;
//		// 上报DTS2B更新参数
//		case REPORT_DTS_DEVICE_PARAMETERS:
//			TypeReference<WSEntity<List<SPEntry>>> spRef = new TypeReference<WSEntity<List<SPEntry>>>() {
//			};
//			entity = CustomObjectMapper.readValue(message.getPayload(), spRef);
//			ConfigurationHandler.handleConfigReportRequest(deviceUUID, entity);
//			break;
//		// 上报DTS2B升降级结果
//		case REPORT_PROMOTE_DTS_FIRMWARE:
//			TypeReference<WSEntity<DevicePromoteResultEntity>> pmtRef = new TypeReference<WSEntity<DevicePromoteResultEntity>>() {
//			};
//			entity = CustomObjectMapper.readValue(message.getPayload(), pmtRef);
//			DeviceRequestHandler.handlePromoteResultReport(deviceUUID, entity);
//			break;
//		// 获取设备激活码
//		case OBTAIN_DTS_DEVICE_ACTIVE_CODE:
//			DeviceRequestHandler.handleActiveCodeObtain(deviceUUID, entity);
//			break;
//			
//		// ####################################RESPONSE############################################
//		// 设置RFID卡权限的响应消息
//		case CONTROLL_DTS_RFID_PERMISSION:
//			OneCardResponseHandler.parseRfidPermControl(deviceUUID, entity);
//			break;
//		// 设备控制响应消息
//		case CONTROLL_DTS_DEVICE:
//			DeviceResponseHandler.handleDeviceControl(deviceUUID, entity);
//			break;
//		case PROMOTE_DTS_FIRMWARE:
//			DeviceResponseHandler.handlePromote(deviceUUID, entity);
//			break;
//		// 查询设备状态
//		case QUERY_DTS_DEVICE_STATUS:
//			TypeReference<WSEntity<List<DeviceStatusEntity>>> trdse = new TypeReference<WSEntity<List<DeviceStatusEntity>>>() {
//			};
//			entity = CustomObjectMapper.readValue(message.getPayload(), trdse);
//			DeviceResponseHandler.handleQueryDeviceStatus(deviceUUID, entity);
//			break;
//		default:
//			break;
//		}
	}

}
