package com.brxy.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brxy.school.model.Device;

/**
*
*@author xiaobing
*@version 2016年4月22日 上午10:37:25
*/
@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

}
