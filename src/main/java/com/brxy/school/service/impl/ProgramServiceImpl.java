package com.brxy.school.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
			//TODO 从文件系统删除文件
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

	@Override
	@Transactional
	public Map<String,Object>  add(String name, String md5, Long duration, String fileExtension, Long fileSize) {
		Map<String,Object> result = new HashMap<>();
		if(fileMD5IsExist(md5)){ 
			result.put("result", false);
			result.put("message", "文件已经存在");
			return result;
		}
		
		//TODO 文件从临时目录复制到正式存储目录暂时不做
		Program p = new Program();
		p.setName(name);
		p.setMd5(md5);
		p.setDuration(duration);
		p.setFileExtension(fileExtension);
		p.setRecordDate(new Date());
		p.setFileSize(fileSize);
		Program program = this.programRepository.save(p);
		result.put("result", true);
		result.put("message", "文件上传成功");
		result.put("program", program);
		return result;
	}
	
	/**
	 * 根据文件MD5判定文件是否已经存在
	 * @param md5
	 * @return
	 */
	private Boolean fileMD5IsExist(String md5){
		Program p = this.programRepository.findProgramByMD5(md5);
		if(null!=p){
			return true;
		}else{
			return false;
		}
		
	}

}
