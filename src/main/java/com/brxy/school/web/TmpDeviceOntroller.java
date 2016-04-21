package com.brxy.school.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brxy.school.common.Firmversion;
import com.brxy.school.model.TmpDevice;

/**
*
*@author xiaobing
*@version 2016年4月19日 下午3:55:23
*/
@Controller
@RequestMapping("/tmpDevice")
public class TmpDeviceOntroller {

	
	private static final Logger logger = LoggerFactory.getLogger(TmpDeviceOntroller.class);
	
	@RequestMapping(value="/main")
	public String getTmpDeviceMain(){
		
		logger.info("getTmpDeviceMain");
		return "addDevice";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public List<TmpDevice> getTmpDeviceList(){
		
		logger.info("getTmpDeviceList");
		
		List<TmpDevice> lists = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			TmpDevice t = new TmpDevice((long) i, "deviceID"+i, "deviceName"+i, Firmversion.DTSK3);
			lists.add(t);
		}
		return lists;
	}
}
