package com.brxy.school.webcoket.config;

/**
 *
 * @author xiaobing
 * @version 2016年6月2日 下午3:05:27
 */
public class WebSocketConstant {

	public static final String DTS2B = "DTS2B";
	public static final String DTS2C = "DTS2C";

	public static final String CONNECTION_HANDLER_URL_PREFIX = "/websocket/connHandler/";
	public static final String CONNECTION_HANDLER_URL_TAIL = "/{deviceId}/{accessCode}";
	public static final String CONNECTION_HANDLER_DTS_URL = "/websocket/connHandler/{deviceId}/{firmVersion}" ;

	public static final int MAX_TEXT_MESSAGE_BUFFER_SIZE = 8192;
	public static final int MAX_BINARY_MESSAGE_BUFFER_SIZE = 8192;
	// public static final int MAX_SESSION_IDEL_TIMEOUT = 300000; //5分钟
	public static final long MAX_SESSION_IDEL_TIMEOUT = 300000; // it is long
																// type for
																// tyrus

	public static final String DEVICE_IDENTITY_LABEL = "deviceId";
	public static final String DEVICE_VERSION_LABEL = "deviceVersion";
	public static final String DEVICE_RESTRICTED_ACTION = "RESTRICTED_ACTION";

	public static final String PERCENTAGE = "Percentage";
	public static final String THEME_ID = "ThemeId";
	public static final String COMMAND = "Command";
	public static final String VALUE = "Value";
	public static final String PROGRAM_ID = "Program_Id";
	public static final String PROGRAM_PUSH_TARGET = "Target";
	public static final String RESOURCE_ID = "Resource_Id";
	public static final String SCHEDULE_ID = "Schedule_Id";
	public static final String FUTURE_DAYS = "Future_Days";
	public static final String STATUS = "Status";
	public static final String HEALTH = "health";
	public static final String DOWNLOAD_KEY = "download_key";
	public static final String SOFT_VERSION = "soft_version";
	public static final String SCHEDULE_OPTION = "Schedule_Option";
	public static final String CLASS_AREA_ID = "Class_Id";
	public static final String LOCATION = "Location";

	public static final String ACTIVE_CODE = "activeCode";
	public static final String FAIL_REASON = "failReason";
	public static final String ACCESS_CODE = "accessCode";

}
