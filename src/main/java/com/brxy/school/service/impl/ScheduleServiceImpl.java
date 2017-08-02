package com.brxy.school.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brxy.school.common.ScheduleRange;
import com.brxy.school.common.Weekday;
import com.brxy.school.model.Device;
import com.brxy.school.model.Program;
import com.brxy.school.model.Schedule;
import com.brxy.school.model.Schema;
import com.brxy.school.repository.ScheduleRepository;
import com.brxy.school.service.DeviceService;
import com.brxy.school.service.ProgramService;
import com.brxy.school.service.ScheduleService;
import com.brxy.school.service.SchemaService;

/**
*
*@author xiaobing
*@version 2016年5月31日 上午10:41:01
*/
@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	@Autowired
	private ScheduleRepository  scheduleRepository;
	
	@Autowired
	private SchemaService schemaService;
	
	@Autowired
	private ProgramService programService;
	
	@Autowired 
	private DeviceService deviceService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

	@Override
	@Transactional
	public void delete(Long scheduleID) {
		this.scheduleRepository.delete(scheduleID);
	}

	@Override
	@Transactional
	public Map<String, Object> addSchedule(Long schemaID, Long index, String startTime, String endTime, Long programID,
			Weekday weekday, ScheduleRange scheduleRange, String deviceIDs) {
		Map<String,Object> result = new HashMap<>();
		
		Schema schema = this.schemaService.findSchema(schemaID);
		Program program = this.programService.findOne(programID);
		if(null!=schema){
			if(null!=program){
				Schedule schedule = new Schedule();
				schedule.setSchema(schema);
				schedule.setIndex(index);
				schedule.setStartTime(startTime);
				schedule.setEndTime(endTime);
				schedule.setProgram(program);
				schedule.setWeekday(weekday);
				schedule.setScheduleRange(scheduleRange);
				if(ScheduleRange.SCHEDULE_PART_DEVICE.equals(scheduleRange)){  //局部计划，则需要关联所选设备
					Set<Device> devices = this.findDevicesFromDeviceIDs(deviceIDs);
					schedule.setDevices(devices);
				}
				Schedule scheduleSaved = this.scheduleRepository.save(schedule);
				result.put("result", true);
				result.put("message", "save schedule success");
				result.put("schedule", scheduleSaved);
			}else{
				// 未找到节目
				result.put("result", false);
				result.put("message", "根据节目id未找到相应的节目");
			}
			
		}else{
			// 未找到方案
			result.put("result", false);
			result.put("message", "根据方案id未找到相应的方案");
		}
		return result;
	}
	
	private Set<Device> findDevicesFromDeviceIDs(String deviceIDs){
		Set<Device> devices = new HashSet<>();
		
		String[] deviceIDArray = deviceIDs.split(",");
		for (int i = 0; i < deviceIDArray.length; i++) {
			Device d = this.deviceService.findOne(deviceIDArray[i]);
			devices.add(d);
		}
		return devices;
	}

	@Override
	@Transactional
	public Map<String, Object> updateSchedule(Long scheduleID, Long index, String startTime, String endTime,
			ScheduleRange scheduleRange, String deviceIDs) {
		Map<String,Object> result = new HashMap<>();
		
		Schedule schedule = this.scheduleRepository.findOne(scheduleID);
		if(null!=schedule){
			schedule.setIndex(index);
			schedule.setStartTime(startTime);
			schedule.setEndTime(endTime);
			if(ScheduleRange.SCHEDULE_PART_DEVICE.equals(scheduleRange)){  //局部计划，则需要关联所选设备
				Set<Device> devices = this.findDevicesFromDeviceIDs(deviceIDs);
				schedule.setDevices(devices);
			}
			this.scheduleRepository.save(schedule);
			result.put("result", true);
			result.put("message", "更新计划任务成功");
		}else{
			result.put("result", false);
			result.put("message", "未找到相应的计划任务");
		}
		return result;
	}

	@Override
	public Schedule getScheduleDetail(Long scheduleID) {
		Schedule schedule = this.scheduleRepository.findOne(scheduleID);
		return schedule;
	}

	@Override
	@Transactional
	public void copySchedule(Weekday sourceDay, Weekday targetDay, Long schemaID) {
		logger.info("copySchedule sourceDay="+sourceDay+" targetDay="+targetDay+" schemaID="+schemaID);
		Schema schema = this.schemaService.findSchema(schemaID);
		Set<Schedule> schedules = schema.getSchedules();
		Set<Schedule> sourceSchedules = new HashSet<>();
		Set<Schedule> targetSchedules = new HashSet<>();
		for (Schedule schedule : schedules) {
			if(schedule.getWeekday().equals(sourceDay)){
				sourceSchedules.add(schedule);
			}else if(schedule.getWeekday().equals(targetDay)){
				targetSchedules.add(schedule);
			}
		}
		logger.info("--------------------------delete sourceday schedule :");
		schedules.removeAll(targetSchedules);
		for (Schedule schedule : targetSchedules) {
			logger.info(schedule.toString());
			this.scheduleRepository.delete(schedule);
		}
		Set<Schedule> newSchedules = copySchedules(sourceSchedules,targetDay);
		schedules.addAll(newSchedules);
		logger.info("--------------------------add copy schedules to targetday:");
		for (Schedule schedule : newSchedules) {
			logger.info(schedule.toString());
		}
		schema.setSchedules(schedules);
		Schema savedSchema = this.schemaService.save(schema);
		logger.info("--------------------------after save copy all schedules :");
		for (Schedule schedule : savedSchema.getSchedules()) {
			logger.info(schedule.toString());
		}
	}

	private Set<Schedule> copySchedules(Set<Schedule> sourceSchedules, Weekday targetDay) {
		Set<Schedule> newSchedules = new HashSet<>();
		for (Schedule schedule : sourceSchedules) {
			Schedule newSchedule = new Schedule();
			String[] ignoreFields = {"id","weekday"};
			BeanUtils.copyProperties(schedule, newSchedule, ignoreFields);
			newSchedule.setWeekday(targetDay);
			newSchedules.add(newSchedule);
		}
		return newSchedules;
	}
	
	

}
