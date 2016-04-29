package com.brxy.school.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brxy.school.model.Program;

/**
*
*@author xiaobing
*@version 2016年4月26日 下午5:12:19
*/
@Controller
@RequestMapping("/program")
public class ProgramController {

	
	private Logger logger = LoggerFactory.getLogger(ProgramController.class);
	
	@ResponseBody
	@RequestMapping(value="findAll")
	public List<Program> findAll(){
		logger.info("find all program");
		List<Program> programs = new ArrayList<Program>();
		for (int i = 0; i < 5; i++) {
			Program program = new Program((long) i,"dance in dangcing"+i, "00:03:25", new Date(), "comment"+i);
			programs.add(program);
			
		}
		return programs;
		
	}
	
	@ResponseBody
	@RequestMapping("deleteProgramFile")
	public Map<String,Object> deleteProgramFile(@RequestParam("programID")Long programID){
		logger.info("delete program file ID="+programID);
		Map<String,Object> result = new HashMap<>();
		try {
			result.put("result", true);
			result.put("message", "delete program file success");
		} catch (Exception e) {
			logger.error("delete program error e"+e.getMessage());
			result.put("result", false);
			result.put("message", "delete program  fail");
		}
		return result;
		
		
	}
}
