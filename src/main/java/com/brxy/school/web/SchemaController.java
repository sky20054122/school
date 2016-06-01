package com.brxy.school.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brxy.school.common.ScheduleRange;
import com.brxy.school.common.SchemaStatus;
import com.brxy.school.common.Weekday;
import com.brxy.school.model.Program;
import com.brxy.school.model.Schedule;
import com.brxy.school.model.Schema;
import com.brxy.school.service.SchemaService;

/**
*
*@author xiaobing
*@version 2016年4月19日 下午3:55:23
*/
@Controller
@RequestMapping("/schema")
public class SchemaController {

	private static final Logger logger = LoggerFactory.getLogger(SchemaController.class);
	
	@Autowired
	private SchemaService schemaService;
	
	
	@RequestMapping("/main")
	public String SchemaMain(){
		logger.info("schema main");
		return "schema";
	}
	
	@ResponseBody
	@RequestMapping("/findAll")
	public Map<String,Object> findAllSchema(){
		Map<String,Object> result = new HashMap<String, Object>();
		logger.info("find all schema");
		List<Schema> lists = this.schemaService.findAll();
		result.put("data", lists);
		
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/addSchema")
	public Map<String,Object> addSchema(@RequestParam("schemaName")String schemaName,@RequestParam("schemaComment")String schemaComment){
		Map<String,Object> result = new HashMap<String, Object>();
		logger.info("add Schema schemaName="+schemaName+"  schemaComment="+schemaComment);
		Schema schema = new Schema();
		schema.setComments(schemaComment);
		schema.setName(schemaName);
		schema.setRecordDate(new Date());
		schema.setSchemaStatus(SchemaStatus.DISABLED);
		Schema savedSchema = this.schemaService.save(schema);
		result.put("result", true);
		result.put("message", "保存计划方案成功");
		result.put("schema", savedSchema);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/enable")
	public Map<String,Object> enableSchema(@RequestParam("schemaID")Long schemaID){
		Map<String,Object> result = new HashMap<String, Object>();
		logger.info("enable Schema id="+schemaID);
		result = this.schemaService.enableSchema(schemaID);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/disable")
	public Map<String,Object> disableSchema(@RequestParam("schemaID")Long schemaID){
		Map<String,Object> result = new HashMap<String, Object>();
		logger.info("disbale Schema id="+schemaID);
		this.schemaService.disableSchema(schemaID);
		result.put("result", true);
		result.put("message", "禁用计划方案成功");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String,Object> deleteSchema(@RequestParam("schemaID")Long schemaID){
		Map<String,Object> result = new HashMap<String, Object>();
		logger.info("delete Schema id="+schemaID);
		this.schemaService.deleteSchema(schemaID);
		result.put("result", true);
		result.put("message", "删除计划方案成功");
		return result;
	}
	
	@RequestMapping("/update/{schemaID}")
	public String updateSchema(@PathVariable("schemaID")Long schemaID,Model model){
		logger.info("updateSchema main");
		model.addAttribute("schemaID", schemaID);
		return "schedule";
	}
	
	@ResponseBody
	@RequestMapping("getSchemaDetail")
	public Schema getSchemaDetail(@RequestParam("schemaID")Long schemaID){
		
		Schema schema = this.schemaService.findSchema(schemaID);
		
		/*schema = new Schema(6L,"schema6",SchemaStatus.ENABLED,"comment",new Date(),new Date());
		Set<Schedule> schedules = schema.getSchedules();
		Program p = new Program(11L,"program name", 56L, new Date(), "eere");
		for (int i = 1; i < 3; i++) {
			Weekday weekday = Weekday.valueOf(i);
			Schedule schedule = new Schedule((long)i, 5000L, "08:00:00", "08:00:15", weekday, p,ScheduleRange.SCHEDULE_ALL_DEVICE);
			schedules.add(schedule);
		}
		
		for (int i = 1; i < 6; i++) {
			Weekday weekday = Weekday.valueOf(i);
			Schedule schedule = new Schedule((long)i, 5000L, "08:00:23", "08:01:15", weekday, p,ScheduleRange.SCHEDULE_PART_DEVICE);
			schedules.add(schedule);
		}
		
		for (int i = 1; i < 6; i++) {
			Weekday weekday = Weekday.valueOf(i);
			Schedule schedule = new Schedule((long)i, 5000L, "11:00:23", "11:01:15", weekday, p,ScheduleRange.SCHEDULE_ALL_DEVICE);
			schedules.add(schedule);
		}*/
		
		
		return schema;
		
	}
}
