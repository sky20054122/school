package com.brxy.school.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brxy.school.model.Program;
import com.brxy.school.repository.ProgramRepository;
import com.brxy.school.service.ProgramService;

/**
*
*@author xiaobing
*@version 2016年4月29日 上午9:49:50
*/
@Service
public class ProgramServiceImpl implements ProgramService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProgramServiceImpl.class);
	
	@Autowired
	private ProgramRepository programRepository;

	@Override
	public List<Program> findAll() {
		return this.programRepository.findAll();
	}

	@Override
	public Program findOne(Long programID) {
		return this.programRepository.findOne(programID);
	}

	@Override
	public Map<String, Object> deleteProgram(Long programID) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			this.programRepository.delete(programID);
			result.put("result", true);
			result.put("message","delete device success");
			logger.info("delete program  programID="+programID);
			
		} catch (Exception e) {
			result.put("result", false);
			result.put("message","delete device fail");
			logger.error("delete program  error "+e.getMessage());
		}
		return result;
	}

	@Override
	public Program save(Program program) {
		Program p = this.programRepository.save(program);
		return p;
	}

}
