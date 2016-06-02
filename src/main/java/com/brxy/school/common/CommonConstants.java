package com.brxy.school.common;

public class CommonConstants {
	
	public static final String TABLE_PREFIX = "brxy_";
	
	
	public static final String CURRENT_USER = "CURRENT_USER";
	public static final String CURRENT_USER_NAME = "username";
	public static final String CURRENT_USER_IP_ADDR = "CURRENT_USER_IP_ADDR";
	public static final String CURRENT_USER_TYPE_AREA = "CURRENT_USER_TYPE_AREA";
	public static final String CURRENT_LOCALE = "CURRENT_LOCALE";
	
	public static final String MONGO_TEMPLATE = "mongoTemplate"; //MongoTemplate实例名称
	public static final String QUERY_DEVICE_SUBVERSION_ALL = "0";  //查询设备过滤条件  子版本0代表查询所有子版本
	public static final String AREA_TYPE_ICON_PREFIX = "/css/zTreeStyle/img/diy/"; //area类型的icon前缀
	
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String HTTPS = "https";
    public static final int DEFAULT_HTTPS_PORT = 443;
    
    public static final String AREA_SPRING_CACHE_NAME = "ecpSpringAreaCache";
    
	private CommonConstants() {
		super();
	}

}
