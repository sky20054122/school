package com.brxy.school.service;

import java.util.List;
import java.util.Map;

import com.brxy.school.model.Schema;

/**
*设备方案
*@author xiaobing
*@version 2016年5月23日 下午4:05:57
*/
public interface SchemaService {

	
	public List<Schema> findAll();
	
	public Map<String,Object> enableSchema(Long schemaID);
	
	public Schema disableSchema(Long schemaID);
	
	public Map<String,Object> deleteSchema(Long schemaID);
	
	public Schema updateSchema(Schema schema);

	public Schema save(Schema schema);

	public Schema findSchema(Long schemaID);
}
