package com.brxy.school.service;

import java.util.List;
import java.util.Map;

import com.brxy.school.model.TmpDevice;

/**
*
*@author xiaobing
*@version 2016年4月22日 上午10:04:43
*/
public interface TmpDeviceService {

	
	public List<TmpDevice> findAll();
	
	public Map<String,Object> actviveTmpDevice(String deviceID);
	
	
}