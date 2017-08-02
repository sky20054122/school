package com.brxy.school.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.brxy.school.common.CommonConstants;
import com.brxy.school.common.SchemaStatus;

/**
 * 計劃方案
 * @author brxy
 *
 */

@Entity
@Table(name=CommonConstants.TABLE_PREFIX+"schema")
public class Schema implements Serializable {

	private static final long serialVersionUID = 6296771820192577871L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name="SCHEMA_STATUS")
	private SchemaStatus schemaStatus;
	
	@Column(name="COMMENTS")
	private String comments;
	
	@Column(name="RECORD_DATE")
	private Date recordDate;
	
	@Column(name="UPDATE_DATE")
	private Date updateDate;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="schema",cascade=CascadeType.ALL)
	private Set<Schedule> schedules = new HashSet<Schedule>();

	

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

	public SchemaStatus getSchemaStatus() {
		return schemaStatus;
	}

	public void setSchemaStatus(SchemaStatus schemaStatus) {
		this.schemaStatus = schemaStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Set<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}

	public Schema() {
		super();
	}
	
	

	public Schema(Long id, String name, SchemaStatus schemaStatus, String comments, Date recordDate, Date updateDate) {
		super();
		this.id = id;
		this.name = name;
		this.schemaStatus = schemaStatus;
		this.comments = comments;
		this.recordDate = recordDate;
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Schema [id=" + id + ", name=" + name + ", schemaStatus=" + schemaStatus + ", comments=" + comments
				+ ", recordDate=" + recordDate + ", updateDate=" + updateDate + "]";
	} 
	

}
