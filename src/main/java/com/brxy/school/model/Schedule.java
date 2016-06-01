package com.brxy.school.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.brxy.school.common.CommonConstants;
import com.brxy.school.common.ScheduleRange;
import com.brxy.school.common.Weekday;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name=CommonConstants.TABLE_PREFIX+"schedule")
public class Schedule implements Serializable {

	private static final long serialVersionUID = 8428564247452884577L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	@Column(name="SCH_INDEX")
	private Long index;
	
	@Column(name="START_TIME")
	private String startTime;
	
	@Column(name="END_TIME")
	private String endTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name="WEEKDAY")
	private Weekday weekday;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FK_PROGRAM_ID")
	private Program program;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FK_SCHEMA_ID")
	private Schema schema;

	@Enumerated(EnumType.STRING)
	@Column(name="SCHEDULE_RANGE")
	private ScheduleRange scheduleRange;
	
	@ManyToMany(fetch=FetchType.EAGER,targetEntity=Device.class)
	@JoinTable(name=CommonConstants.TABLE_PREFIX+"schedule_device",joinColumns={@JoinColumn(name="FK_SCHEDULE_ID")},inverseJoinColumns={@JoinColumn(name="FK_DEVICE_ID")})
	private Set<Device> devices = new HashSet<>();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Weekday getWeekday() {
		return weekday;
	}

	public void setWeekday(Weekday weekday) {
		this.weekday = weekday;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	@JsonIgnore
	public Schema getSchema() {
		return schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	
	

	

	public ScheduleRange getScheduleRange() {
		return scheduleRange;
	}

	public void setScheduleRange(ScheduleRange scheduleRange) {
		this.scheduleRange = scheduleRange;
	}
	
	

	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	public Schedule() {
		super();
	}
	
	

	public Schedule(Long id, Long index, String startTime, String endTime, Weekday weekday, Program program,ScheduleRange scheduleRange) {
		super();
		this.id = id;
		this.index = index;
		this.startTime = startTime;
		this.endTime = endTime;
		this.weekday = weekday;
		this.program = program;
		this.scheduleRange = scheduleRange;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", index=" + index + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", weekday=" + weekday + ", scheduleRange=" + scheduleRange + "]";
	}
	
	
	
}
