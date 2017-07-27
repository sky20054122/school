package com.brxy.school.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.brxy.school.common.CommonConstants;
import com.brxy.school.common.FirmVersion;

@Entity
@Table(name=CommonConstants.TABLE_PREFIX+"temdevice")
public class TempDevice implements Serializable {

	
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="DEVICE_ID",nullable=false,unique=true)
	private String deviceID;
	
	@Column(name="DEVICE_NAME",nullable=false)
	private String deviceName;
	
	@Column(name="FIRMVERSION")
	@Enumerated(EnumType.STRING)
	private FirmVersion firmVersion;


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

	public FirmVersion getFirmVersion() {
		return firmVersion;
	}

	public void setFirmVersion(FirmVersion firmVersion) {
		this.firmVersion = firmVersion;
	}

	@Override
	public String toString() {
		return "TempDevice [deviceID=" + deviceID + ", deviceName=" + deviceName + ", firmVersion="
				+ firmVersion + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceID == null) ? 0 : deviceID.hashCode());
		result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
		result = prime * result + ((firmVersion == null) ? 0 : firmVersion.hashCode());
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
		TempDevice other = (TempDevice) obj;
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
		if (firmVersion != other.firmVersion)
			return false;
		return true;
	}

	public TempDevice() {
		super();
	}

	public TempDevice(String deviceID, String deviceName, FirmVersion firmVersion) {
		super();
		this.deviceID = deviceID;
		this.deviceName = deviceName;
		this.firmVersion = firmVersion;
	}

	
	
	
}
