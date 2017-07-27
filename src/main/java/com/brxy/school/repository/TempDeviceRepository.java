package com.brxy.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brxy.school.model.TempDevice;

/**
*
*@author xiaobing
*@version 2016年4月22日 上午10:09:18
*/
@Repository
public interface TempDeviceRepository extends JpaRepository<TempDevice, String> {

}
