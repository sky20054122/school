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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.brxy.school.common.CommonConstants;
import com.brxy.school.common.DeviceStatus;
import com.brxy.school.model.Device;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 显示设备 液晶电视或者投影仪
 * 
 * @author brxy
 *
 */

@Entity
@Table(name = CommonConstants.TABLE_PREFIX+"displayer")
public class Displayer implements Serializable {

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

	@OneToOne
	@JoinColumn(name = "FK_DEVICE_ID", insertable = true, unique = true)
	private Device device;

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
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Displayer() {
		super();
	}

	
	
	
	

	public Displayer(String identify, DeviceStatus status, Device device) {
		super();
		this.identify = identify;
		this.status = status;
		this.device = device;
	}

	@Override
	public String toString() {
		return "Displayer [id=" + id + ", identify=" + identify + ", status=" + status + "]";
	}

}
