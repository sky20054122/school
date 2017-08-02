/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */

package com.brxy.school.webcoket;


/**
 * @author xiaobing
 * 
 */
public enum WSAction
{
	/**
	 * 拒绝连接
	 */
	REJECT_DTS_CONNECTION,
	/**
	 * 心跳接口
	 */
	KEEP_DTS_HEARTBEAT,
    /**
     * 推送配置参数
     */
    CONFIG_DTS_DEVICE_PARAMETERS ,
    /**
     * 请求同步DTS配置
     */
    REQUEST_SYNC_DTS_ALL_PARAMETERS ,
    /**
     * 请求同步DTS2B开机时间列表
     */
    REQUEST_SYNC_DTS_BOOT_SCHEDULE,
    /**
     * 终端控制
     */
    CONTROLL_DTS_DEVICE ,
    /**
     * 激活设备
     */
    REQUEST_ACTIVE_DTS_DEVICE ,
    /**
     * 终端报告设备状态
     */
    REPORT_DTS_DEVICE_STATUS ,
    /**
     * 查询终端设备状态
     */
    QUERY_DTS_DEVICE_STATUS,
    /**
     * 终端报告设备运行时信息
     */
    REPORT_DTS_DEVICE_RUNTIME_INFO ,
    /**
     * 上传DTS日志
     */
    UPLOAD_DTS_DEVICE_LOG,
    /**
     * 上报DTS刷卡考勤
     */
    REPORT_DTS_RFID_CHECK_ON_DATA,
    /**
     * 请求同步RFID权限
     */
    REQUEST_SYNC_DTS_ALL_RFID_PERMISSION,
    /**
     * 设置RFID权限
     */
    CONTROLL_DTS_RFID_PERMISSION,
    /**
     * 上传DTS2B本地配置
     */
    UPLOAD_DTS_LOCAL_CONFIGURATIONS,
    /**
     * 下载DTS2B本地配置
     */
    DOWNLOAD_DTS_LOCAL_CONFIGURATIONS,
    /**
     * 升级/降级 DTS2B
     */
    PROMOTE_DTS_FIRMWARE,
    /**
     * 上传DTS2B升级降级结果
     */
    REPORT_PROMOTE_DTS_FIRMWARE,
    /**
     * 推送开机时间列表
     */
    CONFIG_DTS_DEVICE_BOOT_SCHEDULE,
    /**
     * 上报DTS2B配置
     */
    REPORT_DTS_DEVICE_PARAMETERS,
    /**
     * 获取设备激活码
     */
    OBTAIN_DTS_DEVICE_ACTIVE_CODE,
    
    
    // ####################分割################################
    /**
     * 推送资源
     */
    PUSH_RESOURCE ,
    /**
     * 删除指定资源
     */
    DELETE_RESOURCE ,
    /**
     * 删除全部资源
     */
    DELETE_ALL_RESOURCES ,
    /**
     * 查询终端已有资源
     */
    QUERY_ALL_RESOURCES ,
    /**
     * 查询资源下载进度
     */
    QUERY_DOWNLOAD_STATUS ,
    /**
     * 推送节目
     */
    PUSH_PROGRAM ,
    /**
     * 删除预置节目
     */
    REMOVE_PRESETPROGRAM ,
    /**
     * 推送计划
     */
    PUSH_SCHEDULE ,
    /**
     * 查询终端所有可用的计划
     */
    QUERY_ACTIVE_SCHEDULES ,
    /**
     * 
     */
    SET_DEVICE_STATUS ,
    /**
     * 获取终端状态
     */
    GET_DEVICE_STATUS ,
    /**
     * 设置主题
     */
    SET_DEVICE_THEME ,
    /**
     * 获取主题
     */
    GET_DEVICE_THEME ,
    /**
     * 设置音量
     */
    SET_DEVICE_VOLUME ,
    /**
     * 获取音量
     */
    GET_DEVICE_VOLUME ,
    /**
     * 设置亮度
     */
    SET_DEVICE_BRIGHTNESS ,
    /**
     * 获取亮度
     */
    GET_DEVICE_BRIGHTNESS ,
    /**
     * 获取磁盘使用信息
     */
    GET_DEVICE_DISK_STATUS ,
    /**
     * 推送升级设备请求
     */
    PUSH_UPGRADE_DEVICE ,
    /**
     * 获取天气信息
     */
    QUERY_WEATHER_INFO ,
    /**
     * 报告刷卡信息
     */
    REPORT_SWIPE_CARD_INFO ,
    /**
     * 报告刷卡注册信息
     */
    REPORT_SWIPE_CARD_REGISTER_INFO ,
    /**
     * 推送课程计划
     */
    PUSH_COURCE_SCHEDULE ,
    /**
     * 通知终端读卡
     */
    READ_CARD_NOTIFICATION ,
    /**
     * 报告没有签到/签退的学生的卡号
     */
    REPORT_NOT_SIGN_INOUT_CARD_IDS ,
    /**
     * 未知命令
     */
    UNKNOWN_ACTION
}
