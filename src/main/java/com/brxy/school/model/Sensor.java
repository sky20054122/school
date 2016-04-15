package com.brxy.school.model;

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

@Entity
@Table(name=CommonConstants.TABLE_PREFIX+"sensor")
public class Sensor implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	@Column(name="IDENTIFY")
	private String identify;

	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	private DeviceStatus status;

	@Column(name="TEMPERATURE")
	private int temperature;

	@Column(name="HUMIDNESS")
	private int humidness;

	@Column(name="ILLUMINATION")
	private int illumination;
	
	@OneToOne
	@JoinColumn(name="FK_DEVICE_ID",insertable=true,unique=true)
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

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getHumidness() {
		return humidness;
	}

	public void setHumidness(int humidness) {
		this.humidness = humidness;
	}

	public int getIllumination() {
		return illumination;
	}

	public void setIllumination(int illumination) {
		this.illumination = illumination;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}


	public Sensor() {
		super();
	}

	@Override
	public String toString() {
		return "Sensor [id=" + id + ", identify=" + identify + ", status=" + status + ", temperature=" + temperature
				+ ", humidness=" + humidness + ", illumination=" + illumination + "]";
	}
	
	

}
