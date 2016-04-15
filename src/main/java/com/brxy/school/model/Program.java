package com.brxy.school.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.brxy.school.common.CommonConstants;

/**
 * 上传文件 包装称节目
 * @author brxy
 *
 */

@Entity
@Table(name=CommonConstants.TABLE_PREFIX+"program")
public class Program implements Serializable {

	private static final long serialVersionUID = -3323841222448250983L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DURATION")
	private String duration;
	
	@Column(name="RECORD_DATE")
	private Date recordDate;
	
	@Column(name="COMMENT")
	private String comment;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="program")
	private Set<Schedule> schedules = new HashSet<>();

	public Program() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Set<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}

	@Override
	public String toString() {
		return "Program [id=" + id + ", name=" + name + ", duration=" + duration + ", recordDate=" + recordDate
				+ ", comment=" + comment + "]";
	}
	
	
	
}
