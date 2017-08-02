package com.brxy.school.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
*
*@author xiaobing
*@version 2016年5月10日 下午3:37:04
*/
@Controller
@RequestMapping("/swipeCardLog")
public class SwipeCardLogController {

	
	private static final Logger logger = LoggerFactory.getLogger(SwipeCardLogController.class);
	
	@RequestMapping(value="/main/{deviceID}/{cardID}")
	public String deviceMain(@PathVariable String deviceID,@PathVariable String cardID){
		logger.info("swipe card log main page deviceID="+deviceID+ "  cardID="+cardID);
		
		return "swipeCardLog";
	}
}
