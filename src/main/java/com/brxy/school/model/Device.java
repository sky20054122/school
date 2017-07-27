package com.brxy.school.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.brxy.school.common.CommonConstants;
import com.brxy.school.common.DeviceStatus;
import com.brxy.school.common.FirmVersion;
import com.brxy.school.model.device.Booth;
import com.brxy.school.model.device.Displayer;
import com.brxy.school.model.device.MediaURL;
import com.brxy.school.model.device.PC;
import com.brxy.school.model.device.PanelSwitch;
import com.brxy.school.model.device.Rfid;
import com.brxy.school.model.device.Screen;
import com.brxy.school.model.device.Sensor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name =CommonConstants.TABLE_PREFIX+ "device")
public class Device implements Serializable {

	private static final long serialVersionUID = -8082389533433108578L;

	/**
	 * 设备编码作为主键
	 */
	@Id
	@Column(name = "DEVICE_ID", unique = true, nullable = false)
	private String deviceId;

	@Column(name = "DEVICE_NAME", nullable = false)
	private String deviceName;

	@Column(name = "FIRMVERSION", nullable = false)
	private FirmVersion firmVersion;

	@Column(name = "DEVICE_STATUS", nullable = false)
	private DeviceStatus deviceStatus;

	@Column(name = "RECORD_DATE")
	private Date recordDate;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "device", cascade = CascadeType.ALL, optional = true)
	private MediaURL mediaURL;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "device", cascade = CascadeType.ALL, optional = true)
	private PC pc;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "device", cascade = CascadeType.ALL, optional = true)
	private Booth booth;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "device", cascade = CascadeType.ALL, optional = true)
	private Displayer displayer;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "device", cascade = CascadeType.ALL, optional = true)
	private Sensor sensor;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "device", cascade = CascadeType.ALL, optional = true)
	private Rfid rfid;

	@OneToMany(mappedBy = "device", fetch = FetchType.LAZY, targetEntity = PanelSwitch.class, cascade = CascadeType.ALL)
	private Set<PanelSwitch> dtsSwitches = new HashSet<PanelSwitch>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "device", cascade = CascadeType.ALL, optional = true)
	private Screen screen;

	/**
	 * 音量 0-15 0表示静音
	 */
	@Column(name = "VOLUME", nullable = false)
	private int volume;

	/**
	 * 显示通道 0内显 1外显
	 */
	@Column(name = "CHANNEL", nullable = false)
	private int channel;

	@ManyToMany(fetch = FetchType.LAZY, targetEntity = Card.class,mappedBy="devices")
	private Set<Card> cards = new HashSet<Card>();

	public Device() {
		super();
	}



	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

	public DeviceStatus getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(DeviceStatus deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public MediaURL getMediaURL() {
		return mediaURL;
	}

	public void setMediaURL(MediaURL mediaURL) {
		this.mediaURL = mediaURL;
	}

	public PC getPc() {
		return pc;
	}

	public void setPc(PC pc) {
		this.pc = pc;
	}

	public Booth getBooth() {
		return booth;
	}

	public void setBooth(Booth booth) {
		this.booth = booth;
	}

	public Displayer getDiplayer() {
		return displayer;
	}

	public void setDisplayer(Displayer displayer) {
		this.displayer = displayer;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Rfid getRfid() {
		return rfid;
	}

	public void setRfid(Rfid rfid) {
		this.rfid = rfid;
	}

	public Set<PanelSwitch> getDtsSwitches() {
		return dtsSwitches;
	}

	public void setDtsSwitches(Set<PanelSwitch> dtsSwitches) {
		this.dtsSwitches = dtsSwitches;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	@JsonIgnore
	public Set<Card> getCards() {
		return cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
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
		Device other = (Device) obj;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
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

	
	
	public Device(String deviceId, String deviceName, FirmVersion firmVersion, DeviceStatus deviceStatus,
			Date recordDate) {
		super();
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.firmVersion = firmVersion;
		this.deviceStatus = deviceStatus;
		this.recordDate = recordDate;
	}



	@Override
	public String toString() {
		return "Device [deviceId=" + deviceId + ", deviceName=" + deviceName + ", firmVersion="
				+ firmVersion + ", deviceStatus=" + deviceStatus + ", recordDate=" + recordDate + ", mediaURL="
				+ mediaURL + ", pc=" + pc + ", booth=" + booth + ", diplayer=" + displayer + ", sensor=" + sensor
				+ ", rfid=" + rfid + ", dtsSwitches=" + dtsSwitches + ", screen=" + screen + ", volume=" + volume
				+ ", channel=" + channel + "]";
	}

}
