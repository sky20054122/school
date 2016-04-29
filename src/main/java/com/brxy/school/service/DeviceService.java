package com.brxy.school.service;

import java.util.List;
import java.util.Map;

import com.brxy.school.model.Device;

/**
*
*@author xiaobing
*@version 2016年4月22日 上午10:41:05
*/
public interface DeviceService {

	
	public Device save(Device device);
	
	public List<Device> findAll();
	
	public Device findOne(String deviceID);

	public Map<String, Object> delete(String deviceID);
	
}
