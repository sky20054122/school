/**
 * 
 */
package com.brxy.school.webcoket;

/**
 * 设备交互错误码
 * 
 * @author Eric
 *
 */
public enum ErrorCode {
	INVALID_CONN_URL, // 连接地址不合法
	DEVICE_NOT_RECORDED, // 设备未录入
	DEVICE_NOT_REGISTERED, // 设备未注册
	INVALID_ACCESS_CODE; // 访问码不合法
}
