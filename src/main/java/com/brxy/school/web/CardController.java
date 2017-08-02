package com.brxy.school.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brxy.school.common.CardRange;
import com.brxy.school.common.DeviceStatus;
import com.brxy.school.common.FirmVersion;
import com.brxy.school.model.Card;
import com.brxy.school.model.Device;

/**
*RFID卡片的管理
*@author xiaobing
*@version 2016年6月1日 下午1:45:26
*/
@Controller
@RequestMapping("card")
public class CardController {

	
	private static final Logger logger = LoggerFactory.getLogger(CardController.class);
	
	@RequestMapping("main")
	public String rfidCardMain(){
		logger.info("wellcome to rfid main page");
		return "card";
	}
	
	@ResponseBody
	@RequestMapping("findAll")
	public Map<String,Object> findAll(){
		logger.info("find all rfid cards");
		Map<String,Object> result = new HashMap<String, Object>();
		
		List<Card> cards = new ArrayList<>();
		Device d = new Device("sdshecjjhe", "device name", FirmVersion.DTSK3A, DeviceStatus.ONLINE	, new Date());
		Set<Device> devices = new HashSet<>();
		devices.add(d);
		for (int i = 0; i < 10; i++) {
			Card card = new Card((long)i, "cardid"+i, "sura", "18908865432", new Date(), new Date(), CardRange.CARD_RANGE_ALL, devices);
			cards.add(card);
		}
		result.put("data", cards);
		result.put("result", true);
		result.put("message", "find all rfid cards success");
		
		
		return result;
		
	}
	
	
}
