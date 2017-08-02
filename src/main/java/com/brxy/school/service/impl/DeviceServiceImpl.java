package com.brxy.school.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brxy.school.common.FirmVersion;
import com.brxy.school.model.Device;
import com.brxy.school.model.Schedule;
import com.brxy.school.repository.DeviceRepository;
import com.brxy.school.service.DeviceService;

/**
*
*@author xiaobing
*@version 2016年4月22日 上午10:43:08
*/
@Service
public class DeviceServiceImpl implements DeviceService {
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);
	
	@Autowired
	private DeviceRepository deviceRepository;

	@Override
	public Device save(Device device) {
		return this.deviceRepository.save(device);
	}

	@Override
	public List<Device> findAll() {
		return this.deviceRepository.findAll();
	}

	@Override
	public Device findOne(String deviceID) {
		return this.deviceRepository.findOne(deviceID);
	}

	@Override
	@Transactional
	public Map<String, Object> delete(String deviceID) {
		Device device = this.deviceRepository.findOne(deviceID);
		Set<Schedule> schedules = device.getSchedules();
		for (Schedule schedule : schedules) {
			schedule.getDevices().remove(device);
		}
		this.deviceRepository.delete(device);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", true);
		result.put("message","delete device success");
		logger.info("delete device deviceID="+deviceID);
		return result;
	}

	@Override
	public FirmVersion getDeviceFirmVersionByID(String deviceUUID) {
		Device d= this.deviceRepository.findOne(deviceUUID);
		return d.getFirmVersion();
	}

}
