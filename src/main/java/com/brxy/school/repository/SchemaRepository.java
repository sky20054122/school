package com.brxy.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brxy.school.common.SchemaStatus;
import com.brxy.school.model.Schema;

/**
*
*@author xiaobing
*@version 2016年5月23日 下午4:13:59
*/
public interface SchemaRepository extends JpaRepository<Schema, Long>{
	
	
	/**
	 * 暂时不使用此方法  可以通过查询修改保存代替
	 * @param schemaStatus
	 * @param schemaID
	 */
	@Modifying
	@Query("update Schema s set s.schemaStatus = :schemaStatus where s.id=:schemaID")
	public void enableOrDisableSchema(@Param("schemaStatus")SchemaStatus schemaStatus,@Param("schemaID")Long schemaID);
	

}
