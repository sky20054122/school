package com.brxy.school.common;
/**
*计划任务的范围  全校计划  （发送到所有设备）
*局部计划，发送到计划关联的设备
*@author xiaobing
*@version 2016年5月27日 上午11:32:44
*/
public enum ScheduleRange {


	/** 全校计划  默认所有设备 不需要关联 */
	SCHEDULE_ALL_DEVICE(1),
	

	/** 局部计划 关联设备 */
	SCHEDULE_PART_DEVICE(0);

	private int value = 0;

	private ScheduleRange(int value) {
		this.value = value;
	}

	public static ScheduleRange valueOf(int value) {
		for (ScheduleRange scheduleRange : ScheduleRange.values()) {
			if (value == scheduleRange.value) {
				return scheduleRange;
			}
		}
		return null;
	}

	

	public int value() {
		return this.value;
	}
	
}
