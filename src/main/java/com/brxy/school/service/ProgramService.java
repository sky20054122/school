package com.brxy.school.service;

import java.util.List;
import java.util.Map;

import com.brxy.school.model.Program;

/**
*
*@author xiaobing
*@version 2016年4月29日 上午9:47:01
*/
public interface ProgramService {
	
	public List<Program> findAll();
	
	public Program findOne(Long programID);
	
	public Map<String,Object> deleteProgram(Long programID);
	
	public Program save(Program program);

}
