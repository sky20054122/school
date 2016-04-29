package com.brxy.school.service.vos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.brxy.school.common.DeviceStatus;
import com.brxy.school.common.Firmversion;
import com.brxy.school.model.device.Booth;
import com.brxy.school.model.device.Displayer;
import com.brxy.school.model.device.MediaURL;
import com.brxy.school.model.device.PC;
import com.brxy.school.model.device.Rfid;
import com.brxy.school.model.device.Screen;
import com.brxy.school.model.device.Sensor;

/**
*
*@author xiaobing
*@version 2016年4月25日 下午4:46:52
*/
public class DeviceDetailVo {

	
	private String deviceId;

	private String deviceName;

	private Firmversion firmversion;

	private DeviceStatus deviceStatus;

	private Date recordDate;

	private MediaURL mediaURL;

	private PC pc;

	private Booth booth;

	private Displayer displayer;

	private Sensor sensor;

	private Rfid rfid;

	private Set<PanelSwitchVo> dtsSwitches = new HashSet<PanelSwitchVo>();

	private Screen screen;

	/**
	 * 音量 0-15 0表示静音
	 */
	private int volume;

	/**
	 * 显示通道 0内显 1外显
	 */
	private int channel;

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

	public Firmversion getFirmversion() {
		return firmversion;
	}

	public void setFirmversion(Firmversion firmversion) {
		this.firmversion = firmversion;
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

	public Displayer getDisplayer() {
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

	public Set<PanelSwitchVo> getDtsSwitches() {
		return dtsSwitches;
	}

	public void setDtsSwitches(Set<PanelSwitchVo> dtsSwitches) {
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
	
	
	

}
