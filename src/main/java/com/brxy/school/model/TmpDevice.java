package com.brxy.school.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.brxy.school.common.CommonConstants;
import com.brxy.school.common.Firmversion;

@Entity
@Table(name=CommonConstants.TABLE_PREFIX+"temdevice")
public class TmpDevice implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	@Column(name="DEVICE_ID",nullable=false,unique=true)
	private String deviceID;
	
	@Column(name="DEVICE_NAME",nullable=false)
	private String deviceName;
	
	@Column(name="FIRMVERSION")
	@Enumerated(EnumType.STRING)
	private Firmversion firmversion;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Firmversion getFirmversion() {
		return firmversion;
	}

	public void setFirmversion(Firmversion firmversion) {
		this.firmversion = firmversion;
	}

	@Override
	public String toString() {
		return "TmpDevice [id=" + id + ", deviceID=" + deviceID + ", deviceName=" + deviceName + ", firmversion="
				+ firmversion + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceID == null) ? 0 : deviceID.hashCode());
		result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
		result = prime * result + ((firmversion == null) ? 0 : firmversion.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TmpDevice other = (TmpDevice) obj;
		if (deviceID == null) {
			if (other.deviceID != null)
				return false;
		} else if (!deviceID.equals(other.deviceID))
			return false;
		if (deviceName == null) {
			if (other.deviceName != null)
				return false;
		} else if (!deviceName.equals(other.deviceName))
			return false;
		if (firmversion != other.firmversion)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	public TmpDevice() {
		super();
	}

	public TmpDevice(String deviceID, String deviceName, Firmversion firmversion) {
		super();
		this.deviceID = deviceID;
		this.deviceName = deviceName;
		this.firmversion = firmversion;
	}

	public TmpDevice(Long id, String deviceID, String deviceName, Firmversion firmversion) {
		super();
		this.id = id;
		this.deviceID = deviceID;
		this.deviceName = deviceName;
		this.firmversion = firmversion;
	}
	
	
	
}
