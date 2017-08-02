/**
 * 
 */
package com.brxy.school.service.domain;

import java.io.Serializable;

import com.brxy.school.websocket.protocol.WSStatus;



/**
 * 返回结果
 * 
 * @author Eric
 *
 */
public class ResponseResult implements Serializable{
	private static final long serialVersionUID = 2784774240198091011L;
	// 设备ID
	private String deviceUUID;
	// 是否成功
	private WSStatus statusCode;
	// 成功实体或失败原因
	private Object result;

	public ResponseResult() {
		super();
	}
	
	public ResponseResult(String deviceUUID, WSStatus statusCode, Object result) {
		super();
		this.deviceUUID = deviceUUID;
		this.statusCode = statusCode;
		this.result = result;
	}
	
	public boolean isSuccess()
	{
		return this.statusCode==WSStatus.SUCCESS?true:false;
	}

	public String getDeviceUUID() {
		return deviceUUID;
	}

	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}

	public WSStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(WSStatus statusCode) {
		this.statusCode = statusCode;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ResponseResult [deviceUUID=" + deviceUUID + ", statusCode="
				+ statusCode + ", result=" + result + "]";
	}

}
