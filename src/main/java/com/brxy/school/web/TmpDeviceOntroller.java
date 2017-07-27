package com.brxy.school.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brxy.school.model.TempDevice;
import com.brxy.school.service.TmpDeviceService;

/**
*
*@author xiaobing
*@version 2016年4月19日 下午3:55:23
*/
@Controller
@RequestMapping("/tmpDevice")
public class TmpDeviceOntroller {

	
	private static final Logger logger = LoggerFactory.getLogger(TmpDeviceOntroller.class);
	
	@Autowired
	private TmpDeviceService tmpDeviceService;
	
	@RequestMapping(value="/main")
	public String getTmpDeviceMain(){
		
		logger.info("getTmpDeviceMain");
		return "addDevice";
	}
	
	@ResponseBody
	@RequestMapping(value="/list")
	public List<TempDevice> getTmpDeviceList(){
		logger.info("getTmpDeviceList");
		return this.tmpDeviceService.findAll();
	}
	
	@ResponseBody
	@RequestMapping(value="/active")
	public Map<String,Object> activeTmpDevice(@RequestParam("deviceID")String deviceID){
		
		logger.info("activeTmpDevice");
		return this.tmpDeviceService.actviveTmpDevice(deviceID);
	}
}
