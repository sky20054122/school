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
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	/**
	 * 文件的md5值 ，在文件系统中作为文件的名称  例如 md5.mp3
	 */
	@Column(name="MD5")
	private String md5;
	
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

	@JsonIgnore
	public Set<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}
	
	

	public String getMd5() {
		return md5;
	}


	public void setMd5(String md5) {
		this.md5 = md5;
	}


	@Override
	public String toString() {
		return "Program [id=" + id + ", name=" + name + ", duration=" + duration + ", recordDate=" + recordDate
				+ ", comment=" + comment + "]";
	}

	

	public Program(String name, String duration, Date recordDate, String comment) {
		super();
		this.name = name;
		this.duration = duration;
		this.recordDate = recordDate;
		this.comment = comment;
	}


	public Program(Long id, String name, String duration, Date recordDate, String comment) {
		super();
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.recordDate = recordDate;
		this.comment = comment;
	}


	
	
	
	
}
