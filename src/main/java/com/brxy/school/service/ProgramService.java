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
	
	/**
	 * 
	 * @param name  文件原始名称
	 * @param md5 文件的MD5
	 * @param duration  音频持续时长
	 * @param fileExtension 文件的扩展名
	 * @param fileSize 文件大小 单位字节
	 * @return
	 */
	public Map<String,Object> add(String name,String md5,Long duration,String fileExtension, Long fileSize);

}
