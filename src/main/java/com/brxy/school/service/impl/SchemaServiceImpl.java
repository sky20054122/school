package com.brxy.school.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brxy.school.common.SchemaStatus;
import com.brxy.school.model.Schema;
import com.brxy.school.repository.SchemaRepository;
import com.brxy.school.service.SchemaService;

/**
*
*@author xiaobing
*@version 2016年5月23日 下午4:10:20
*/
@Service
public class SchemaServiceImpl implements SchemaService {
	
	@Autowired
	private SchemaRepository schemaRepository;

	@Override
	public List<Schema> findAll() {
		return this.schemaRepository.findAll();
	}

	@Override
	@Transactional
	public Map<String,Object> enableSchema(Long schemaID) {
		Map<String,Object> result = new HashMap<>();
		List<Schema> lists = this.findAll();
		Schema enableSchema = null;
		for (Schema schema : lists) {
			if(schemaID==schema.getId()){
				enableSchema = schema;
			}else if(schema.getSchemaStatus().equals(SchemaStatus.ENABLED)){
				enableSchema = null;
				break;  //有其他设备处于启用状态
			}
		}
		if(null!=enableSchema){
			enableSchema.setSchemaStatus(SchemaStatus.ENABLED);
			enableSchema.setUpdateDate(new Date(System.currentTimeMillis()));
			this.schemaRepository.save(enableSchema);
			result.put("result", true);
			result.put("message", "启用成功！");
		}else{
			result.put("result", false);
			result.put("message", "启用失败，请先禁用当前正在使用的计划！");
		}
		return result;
	}

	@Override
	@Transactional
	public Schema disableSchema(Long schemaID) {
		Schema schema = this.schemaRepository.findOne(schemaID);
		schema.setSchemaStatus(SchemaStatus.DISABLED);
		schema.setUpdateDate(new Date(System.currentTimeMillis())); 
		this.schemaRepository.enableOrDisableSchema(SchemaStatus.DISABLED,schemaID);
		return this.schemaRepository.save(schema);
	}

	@Override
	@Transactional
	public Map<String, Object> deleteSchema(Long schemaID) {
		Map<String,Object> result = new HashMap<>();
		this.schemaRepository.delete(schemaID);
		return result;
	}

	@Override
	@Transactional
	public Schema updateSchema(Schema schema) {
		
		return this.schemaRepository.save(schema);
	}

	@Override
	@Transactional
	public Schema save(Schema schema) {
		return this.schemaRepository.save(schema);
	}

	@Override
	public Schema findSchema(Long schemaID) {
		
		return this.schemaRepository.findOne(schemaID);
	}

}
