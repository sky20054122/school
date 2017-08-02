package com.brxy.school.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brxy.school.model.TempDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brxy.school.service.TempDeviceService;

/**
*
*@author xiaobing
*@version 2016年4月19日 下午3:55:23
*/
@Controller
@RequestMapping("/tmpDevice")
public class TempDeviceController {

	
	private static final Logger logger = LoggerFactory.getLogger(TempDeviceController.class);
	
	@Autowired
	private TempDeviceService tempDeviceService;
	
	@RequestMapping(value="/main")
	public String getTmpDeviceMain(){
		
		logger.info("getTmpDeviceMain");
		return "addDevice";
	}
	
	@ResponseBody
	@RequestMapping(value="/list")
	public Map<String,Object> getTmpDeviceList(){
		logger.info("getTmpDeviceList");
		List<TempDevice> list = this.tempDeviceService.findAll();
		Map<String,Object> result = new HashMap<>();
		result.put("data", list);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/active")
	public Map<String,Object> activeTmpDevice(@RequestParam("deviceID")String deviceID){
		
		logger.info("activeTmpDevice");
		return this.tempDeviceService.actviveTmpDevice(deviceID);
	}
}
