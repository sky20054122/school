package com.brxy.school.service.vos;

import com.brxy.school.common.DeviceStatus;

/**
*
*@author xiaobing
*@version 2016年4月25日 下午6:08:00
*/
public class ChildSwitchVo {

	private Long id;

	private String identify;

	private DeviceStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public DeviceStatus getStatus() {
		return status;
	}

	public void setStatus(DeviceStatus status) {
		this.status = status;
	}
	
	
	
}
