package com.brxy.school.model.device;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.brxy.school.common.CommonConstants;
import com.brxy.school.common.DeviceStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 每个开关面板上的某一个具体的开关
 * 
 * @author brxy
 *
 */

@Entity
@Table(name =CommonConstants.TABLE_PREFIX+ "childswitch")
public class ChildSwitch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "IDENTIFY")
	private String identify;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private DeviceStatus status;

	@ManyToOne
	@JoinColumn(name = "FK_PANEL_SWITCH_ID")
	private PanelSwitch panelSwitch;

	public ChildSwitch() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public DeviceStatus getStatus() {
		return status;
	}

	public void setStatus(DeviceStatus status) {
		this.status = status;
	}

	@JsonIgnore
	public PanelSwitch getPanelSwitch() {
		return panelSwitch;
	}

	public void setPanelSwitch(PanelSwitch panelSwitch) {
		this.panelSwitch = panelSwitch;
	}

	@Override
	public String toString() {
		return "ChildSwitch [id=" + id + ", identify=" + identify + ", status=" + status + "]";
	}

	public ChildSwitch(String identify, DeviceStatus status, PanelSwitch panelSwitch) {
		super();
		this.identify = identify;
		this.status = status;
		this.panelSwitch = panelSwitch;
	}

	
}
