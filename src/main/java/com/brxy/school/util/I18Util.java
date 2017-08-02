package com.brxy.school.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brxy.school.exceptions.SystemErrorCode;


public class I18Util {
	private static Logger logger = LoggerFactory.getLogger(I18Util.class);
	public static final String PARAMETER_EMPTY_KEY = "COMMON_1000";

	private static final Locale DEFAILT_LOCALE = Locale.CHINA;
	private static final String BASE_PACKAGE = "com.brxy.mpush.messages";
	//private static final Map<AppModule, Map<Locale, Map<String, String>>> localeMessages = new HashMap<AppModule, Map<Locale, Map<String, String>>>();
	private static final String RESOURCEBUNDLE_DEFAULT_DECODING = "ISO-8859-1";
	private static final String PROPERTIES_FILE_ENCODING = "UTF-8";

	private I18Util() {
		super();
	}

//	public static String getMessage(SystemErrorCode errorCode,
//			Object... messageParameters) {
//		return getMessage(errorCode.getErrorKey(), errorCode.getErrorKey(),
//				CommonUtils.getCurrentLocale(), messageParameters);
//	}



	

	

	public static String getMessage(String messageKey,
			Object... messageParameters) {
		return getMessage(messageKey, null, messageParameters);
	}

	

	
}