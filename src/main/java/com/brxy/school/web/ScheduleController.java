package com.brxy.school.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brxy.school.common.ScheduleRange;
import com.brxy.school.common.Weekday;
import com.brxy.school.model.Schedule;
import com.brxy.school.service.ScheduleService;

/**
*
*@author xiaobing
*@version 2016年5月30日 下午3:29:13
*/
@Controller
@RequestMapping("schedule")
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
	
	@ResponseBody
	@RequestMapping("saveSchedule")
	public Map<String,Object> saveSchedule(
			@RequestParam("schemaID")Long schemaID,
			@RequestParam("index")Long index,
			@RequestParam("startTime")String startTime,
			@RequestParam("endTime")String endTime,
			@RequestParam("programID")Long programID,
			@RequestParam("weekday")Weekday weekday,
			@RequestParam("scheduleRange")ScheduleRange scheduleRange,
			@RequestParam("deviceIDs")String deviceIDs){
		Map<String,Object> result = new HashMap<>();
		
		logger.info("schemaID="+schemaID+"  index="+index+" addStartTime="+startTime+" addEndTime="+endTime+" programID="+programID+" weekday="+weekday+" scheduleRange="+scheduleRange+" deviceIDs="+deviceIDs);
		result = this.scheduleService.addSchedule(schemaID,index,startTime,endTime,programID,weekday,scheduleRange,deviceIDs);
		result.put("result", true);
		result.put("message", "save schedule success");
		return result;
	    	
	}
	
	
	@ResponseBody
	@RequestMapping("getScheduleDetail")
	public Map<String,Object> getScheduleDetail(@RequestParam("scheduleID")Long scheduleID){
		Map<String,Object> result = new HashMap<>();
		
		Schedule schedule = this.scheduleService.getScheduleDetail(scheduleID);
		result.put("result", true);
		result.put("message", "获取计划任务详情成功");
		result.put("schedule", schedule);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public Map<String,Object> delete(@RequestParam("scheduleID")Long scheduleID){
		Map<String,Object> result = new HashMap<>();
		
		this.scheduleService.delete(scheduleID);
		result.put("result", true);
		result.put("message", "删除计划任务成功");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("update")
	public Map<String,Object> update(
			@RequestParam("index")Long index,
			@RequestParam("startTime")String startTime,
			@RequestParam("endTime")String endTime,
			@RequestParam("scheduleID")Long scheduleID,
			@RequestParam("scheduleRange")ScheduleRange scheduleRange,
			@RequestParam("deviceIDs")String deviceIDs){
		Map<String,Object> result = new HashMap<>();
		
	//	index,startTime,endTime,scheduleID,scheduleRange,deviceIDs
		result = this.scheduleService.updateSchedule(scheduleID,index,startTime,endTime,scheduleRange,deviceIDs);
		
		return result;
		
	}
	
	@ResponseBody
	@RequestMapping("copySchedule")
	public Map<String,Object> copySchedule(
			@RequestParam("sourceDay")int sourceDay,
			@RequestParam("targetDay")int targetDay,
			@RequestParam("schemaID")Long schemaID){
		
		Map<String,Object> result = new HashMap<>();
		Weekday source = Weekday.valueOf(sourceDay);
		Weekday target = Weekday.valueOf(targetDay);
		this.scheduleService.copySchedule(source,target,schemaID);
		result.put("result", true);
		result.put("message", "复制计划任务成功");
		return result;
	}

}
