package com.brxy.school.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brxy.school.common.DeviceStatus;
import com.brxy.school.common.DeviceType;
import com.brxy.school.common.FirmVersion;
import com.brxy.school.common.OperationType;
import com.brxy.school.model.Device;
import com.brxy.school.service.DeviceService;

/**
*
*@author xiaobing
*@version 2016年4月19日 下午3:55:23
*/

@Controller
@RequestMapping(value="/device")
public class DeviceController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

	@Autowired
	private DeviceService deviceService;
	
	@RequestMapping(value="/main")
	public String deviceMain(){
		return "deviceMonitor";
	}
	
	@ResponseBody
	@RequestMapping("queryMonitorDevice")
	public List<Device> findAll(@RequestParam("deviceID")String deviceID,
			@RequestParam("deviceName")String deviceName,
			@RequestParam("deviceVersion")FirmVersion firmVersion,
			@RequestParam("deviceStatus")DeviceStatus deviceStatus){
		
		logger.debug(deviceID+"  "+deviceName+" "+deviceStatus+" "+ firmVersion);
		return this.deviceService.findAll();
	}
	
	
	@ResponseBody
	@RequestMapping(value="getDeviceDetail")
	public Map<String,Object> getDeviceDetail(@RequestParam("deviceID")String deviceID){
		Map<String,Object> result = new HashMap<>();
		try {
			Device device = this.deviceService.findOne(deviceID);
			result.put("result", true);
			result.put("device", device);
		} catch (Exception e) {
			logger.error("getDeviceDetail error e"+e.getMessage());
			result.put("result", false);
			result.put("message", "get device detail fail");
		}
		return result;
		
	}
	
	@ResponseBody
	@RequestMapping(value="deleteDevice")
	public Map<String,Object> deleteDevice(@RequestParam("deviceID")String deviceID){
		return this.deviceService.delete(deviceID);
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="batchControllDevice")
	public Map<String,Object> batchControllDevice(@RequestParam("deviceIDs")String deviceIDs,
			@RequestParam("deviceType")DeviceType deviceType,
			@RequestParam("operationCode")OperationType operationType){
		
		logger.info("batchControllDevice deviceIDs="+deviceIDs+"  deviceType="+deviceType+"  operationType="+operationType);
		Map<String,Object> result = new HashMap<>();
		try {
			//TODO 
			result.put("result", true);
			result.put("message", "batch cntroll device success");
		} catch (Exception e) {
			logger.error("batchControllDevice error e"+e.getMessage());
			result.put("result", false);
			result.put("message", "batch Controll Device detail fail");
		}
		return result;
	}
	
	
	/**
	 * 控制设备函数
	 * deviceID 设备的id 用于区分控制那个设备
	 * deviceTypeCode 设备类型
	 * operationCode 设备操作类型
	 * ID 操作设备ID  主要用于开关控制
	 * operationValue 操作值  主要音量控制的值
	 */
	@ResponseBody
	@RequestMapping(value="deviceControll")
	public Map<String,Object> deviceControll(@RequestParam("deviceID")String deviceID,
			@RequestParam("deviceType")DeviceType deviceType,
			@RequestParam("operationCode")OperationType operationType,
			@RequestParam("operationValue")String operationValue,
			@RequestParam("ID")String ID){
		
		logger.info("deviceControll deviceID="+deviceID+" deviceType="+deviceType+"  operationType="+operationType+" operationValue="+operationValue+" ID="+ID);
		Map<String,Object> result = new HashMap<>();
		try {
			//TODO 
			result.put("result", true);
			result.put("message", "cntroll device success");
		} catch (Exception e) {
			logger.error("batchControllDevice error e"+e.getMessage());
			result.put("result", false);
			result.put("message", "Controll Device detail fail");
		}
		return result;
		
	} 
	
	@ResponseBody
	@RequestMapping(value="broadcast")
	public Map<String,Object> broadcast(@RequestParam("deviceIDs")String deviceIDs,
			@RequestParam("programID")Long programID){
		
		logger.info("broadcast deviceIDs="+deviceIDs+" programID="+programID);
		Map<String,Object> result = new HashMap<>();
		try {
			//TODO 
			result.put("result", true);
			result.put("message", "broadcast success");
		} catch (Exception e) {
			logger.error("broadcast error e"+e.getMessage());
			result.put("result", false);
			result.put("message", "broadcast fail");
		}
		return result;
	}
}
