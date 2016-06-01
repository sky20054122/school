package com.brxy.school.service;

import java.util.Map;

import com.brxy.school.common.ScheduleRange;
import com.brxy.school.common.Weekday;
import com.brxy.school.model.Schedule;

/**
*
*@author xiaobing
*@version 2016年5月31日 上午10:40:31
*/
public interface ScheduleService {
	
	public Schedule getScheduleDetail(Long scheduleID);

	/**
	 * 根据id删除计划任务
	 * @param scheduleID
	 */
	public void delete(Long scheduleID);
	
	/**
	 * 保存新的计划任务  例如一次铃声
	 * @param schemaID 计划任务所属方案
	 * @param index  计划任务开始时间排序数值
	 * @param startTime 开始时间  时分秒格式
	 * @param endTime 结束时间
	 * @param programID 关联节目的id
	 * @param weekday 星期几
	 * @param scheduleRange 计划任务所属范围  是否包含设备
	 * @param deviceIDs  计划任务关联设备的id集合
	 * @return 返回保存结果
	 */
	public Map<String,Object> addSchedule(Long schemaID, Long index, String startTime, String endTime, Long programID, Weekday weekday, ScheduleRange scheduleRange, String deviceIDs);
	
	/**
	 * 更新计划任务
	 * @param scheduleID  计划任务的id
	 * @param index 计划任务开始时间排序数值
	 * @param startTime 开始时间  时分秒格式
	 * @param endTime 结束时间
	 * @param scheduleRange 计划任务所属范围  是否包含设备
	 * @return 返回更新结果
	 */
	public Map<String,Object> updateSchedule(Long scheduleID, Long index, String startTime, String endTime, ScheduleRange scheduleRange, String deviceIDs);

	/**
	 * 复制某天的计划到另外一天
	 * @param sourceDay
	 * @param targetDay
	 * @param schemaID
	 */
	public void copySchedule(Weekday sourceDay, Weekday targetDay, Long schemaID);
}
