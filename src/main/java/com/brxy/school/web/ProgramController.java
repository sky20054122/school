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

import com.brxy.school.model.Program;
import com.brxy.school.service.ProgramService;

/**
*
*@author xiaobing
*@version 2016年4月26日 下午5:12:19
*文件删除 查询
*/
@Controller
@RequestMapping("/program")
public class ProgramController {

	
	private Logger logger = LoggerFactory.getLogger(ProgramController.class);
	
	@Autowired
	private ProgramService programService;
	
	@ResponseBody
	@RequestMapping(value="findAll")
	public Map<String,Object> findAll(){
		Map<String,Object> result = new HashMap<>();
		logger.info("find all program");
		List<Program> programs = this.programService.findAll();		
		result.put("data", programs);
		return result;
		
	}
	
	@ResponseBody
	@RequestMapping("deleteProgramFile")
	public Map<String,Object> deleteProgramFile(@RequestParam("programID")Long programID){
		logger.info("delete program file ID="+programID);
		Map<String,Object> result = new HashMap<>();
		try {
			this.programService.deleteProgram(programID);
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
