package com.brxy.school.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brxy.school.common.DeviceStatus;
import com.brxy.school.common.Firmversion;
import com.brxy.school.model.Device;
import com.brxy.school.model.TmpDevice;
import com.brxy.school.model.device.ChildSwitch;
import com.brxy.school.model.device.Displayer;
import com.brxy.school.model.device.MediaURL;
import com.brxy.school.model.device.PC;
import com.brxy.school.model.device.PanelSwitch;
import com.brxy.school.model.device.Rfid;
import com.brxy.school.model.device.Screen;
import com.brxy.school.model.device.Sensor;
import com.brxy.school.repository.DeviceRepository;
import com.brxy.school.repository.TmpdeviceRespository;
import com.brxy.school.service.TmpDeviceService;

/**
*
*@author xiaobing
*@version 2016年4月22日 上午10:06:49
*/
@Service
public class TmpDeviceServiceImpl implements TmpDeviceService {
	
	private static final Logger logger = LoggerFactory.getLogger(TmpDeviceServiceImpl.class);

	@Autowired
	private TmpdeviceRespository tmpdeviceRespository;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Override
	public List<TmpDevice> findAll() {
		List<TmpDevice> list = this.tmpdeviceRespository.findAll();
		logger.info("find all tmpDevice");
		//添加测试数据
		if(list.isEmpty()||list.size()<20){
			
			addTmpDeviceDemo();
			
		}
		
		return list;
	}

	@Transactional
	private void addTmpDeviceDemo() {
		for (int i = 0; i < 2500; i++) {
			String uuid = UUID.randomUUID().toString();
			TmpDevice t = new TmpDevice( uuid+i, uuid+i, Firmversion.DTSK3);
			TmpDevice td = this.tmpdeviceRespository.save(t);
			logger.info("create demo tmpDevice"+td.toString());
		}
		
	}

	/**
	 * 激活设备 
	 * 把设备从临时表存储到正式表 TmpDevice --》 Device
	 */
	@Override
	@Transactional
	public Map<String, Object> actviveTmpDevice(String deviceID) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		TmpDevice t = this.tmpdeviceRespository.findOne(deviceID);
		if(null!=t){
			Device d = new Device(t.getDeviceID(), t.getDeviceName(), t.getFirmversion(), DeviceStatus.OFFLINE, new Date());
			
			//TODO 测试假数据    自动补齐设备基本信息  pc rfid 。。。
			PC pc = new PC("el_pc", DeviceStatus.ONLINE,d);
			d.setPc(pc);
			d.setVolume(8);
			MediaURL mediaURL = new MediaURL("rtmp://cp67126.edgefcs.net/ondemand/mp4:mediapm/ovp/content/test/video/spacealonehd_sounas_640_300.mp4", "rtmp://live.hkstv.hk.lxdns.com/live/hks",d);
			d.setMediaURL(mediaURL);
			Displayer displayer = new Displayer("displayer", DeviceStatus.ONLINE,d);
			d.setDisplayer(displayer);
			Rfid rfid = new Rfid("rfid", DeviceStatus.OFFLINE,d);
			d.setRfid(rfid);
			Screen screen = new Screen("熒幕控制器",DeviceStatus.ONLINE,d);
			d.setScreen(screen);
			Sensor sensor = new Sensor("sensor",DeviceStatus.ONLINE,30,65,1200,d);
			d.setSensor(sensor);
			Set<PanelSwitch> dtsSwitches = d.getDtsSwitches();
			Set<ChildSwitch> child = new HashSet<>();
			PanelSwitch panelSwitch = new PanelSwitch(child,d,"开关1","11:22:33:44:55:66");
			ChildSwitch childSwitch01 = new ChildSwitch("switch01", DeviceStatus.ONLINE, panelSwitch);
			ChildSwitch childSwitch02 = new ChildSwitch("switch02", DeviceStatus.ONLINE, panelSwitch);
			child.add(childSwitch02);
			child.add(childSwitch01);
			panelSwitch.setChild(child);
			
			dtsSwitches.add(panelSwitch);
			this.deviceRepository.save(d);
			
			
			this.tmpdeviceRespository.delete(deviceID);
		}else{
			result.put("result", false);
			result.put("message", "not find TmpDevice by deviceID="+deviceID);
		}
		
		result.put("result", true);
		result.put("message", "success");
		return result;
	}

}
