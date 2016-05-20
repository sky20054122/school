
/**
 * 数字教学一体机类型 deviceType
 * 
 * DTS_HOST 数字教学一体机 – 主机 DTS_PC 数字教学一体机 – 内嵌 PC DTS_DISPLAYER 连接 DTS 的显示设备
 * DTS_SWITCH 开关 DTS_VOLUME 音量 DTS_RFID RFID读卡器 DTS_SENSOR 温度、湿度、亮度传感器
 * DTS_CHANNEL 显示通道 DTS_BOOTH 展台
 */
function DeviceType(){
    this.dtsHost = "DTS_HOST";  //教学一体机主机
    this.dtsPC = "DTS_PC";  //内置电脑
    this.dtsDisplayer = "DTS_DISPLAYER";  //外显
    this.dtsSwitch = "DTS_SWITCH";  //开关
    this.dtsVolume = "DTS_VOLUME";  //音量
    this.dtsRfid = "DTS_RFID";  //RFID刷卡器
    this.dtsSensor = "DTS_SENSOR";  //温度湿度亮度传感器
    this.dtsChannel = "DTS_CHANNEL";  //显示通道
    this.dtsBooth = "DTS_BOOTH";  //展台
    this.dtsScreen = "DTS_SCREEN"; //荧幕控制器 同步外显  打开外显同时打开荧幕控制器  ，关闭外显，关闭荧幕控制器
}
var deviceType = new DeviceType();


/**
 * 数字教学一体机操作 operationCode
 * 
 * START 打开设备 SHUTDOWN 关闭设备 RESTART 重启设备 ADJUST 设备在打开状态下 调节设备 READ 设备在打开状态下 读取值
 * ENABLE 启用设备 DISABLE 禁用设备
 */
function Operation(){
	this.start = "START";  //打开设备  主要针对 DTS_HOST、DTS_PC、DTS_DISPLAYER、DTS_SWITCH、DTS_BOOTH
	this.shutdown = "SHUTDOWN"; //关闭设备   主要针对 DTS_HOST、DTS_PC、DTS_DISPLAYER、DTS_SWITCH、DTS_BOOTH
	this.restart = "RESTART"; //重启设备
	this.adjust = "ADJUST"; //设备在打开状态下 调节设备  主要针对DTS_VOLUME DTS_CHANNEL
	this.read = "READ";  //设备在打开状态下 读取值
	this.enable = "ENABLE";  //启用设备
	this.disable = "DISABLE";  // 禁用设备
}
var operation = new Operation();


/**
 * 设备运行状态 deviceStatus 
 */
function DeviceStatus(){
	this.onLine = "ONLINE";//在线状态   待机状态和运行状态都是在线状态的一种
	this.standby = "STANDBY";//待机状态
	this.working = "WORKING";//运行状态
	this.offLine = "OFFLINE";//离线状态
	this.inside = "0";//内显
	this.outside = "1";//外显
}
var deviceStatus = new DeviceStatus();