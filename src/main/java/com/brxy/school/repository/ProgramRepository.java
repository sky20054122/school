package com.brxy.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brxy.school.model.Program;

/**
*
*@author xiaobing
*@version 2016年4月29日 上午9:50:44
*/
public interface ProgramRepository extends JpaRepository<Program, Long>{

	@Query("select p from Program p where p.md5=:md5")
	public Program findProgramByMD5(@Param("md5")String md5);
}
