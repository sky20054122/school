package com.brxy.school.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.brxy.school.common.SpringUtil;



public class CommonUtils {
	private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	private static String APP_HOME = "";
	private static SpringUtil springUtil = null;
	private static String serverHostName;

	public static void setAppHome(String appHome) {
		APP_HOME = appHome;
	}

	public static String getAppHome() {
		return APP_HOME;
	}

	public static boolean isDigit(String str) {
		if (isEmpty(str)) {
			return false;
		}
		try {
			Double.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isEmpty(Collection<? extends Object> value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Map<? extends Object, ? extends Object> value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Object value) {
		return value == null;
	}

	public static boolean isEmpty(Object[] value) {
		if (value == null || value.length < 1) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String value) {
		if (value == null || value.length() == 0) {
			return true;
		}
		return false;
	}

	public static Object getBean(String beanName) {
		if (isEmpty(springUtil)) {
			throw new IllegalAccessError("The Spring Util is empty");
		} else {
			return springUtil.getBean(beanName);
		}
	}

	public static <T> T getBean(Class<T> classz) {
		if (isEmpty(springUtil)) {
			throw new IllegalAccessError("The Spring Util is empty");
		} else {
			return springUtil.getBean(classz);
		}
	}

	public static Object getApplicationContext() {
		if (isEmpty(springUtil)) {
			throw new IllegalAccessError("The Spring Util is empty");
		} else {
			return springUtil.getApplicationContext();
		}
	}

	public synchronized static void setSpringUtil(SpringUtil springUtil) {
		CommonUtils.springUtil = springUtil;
	}

	public static boolean isSpringUtilEmpty() {
		return CommonUtils.springUtil == null;
	}

//	public static Object getCurrentUser() {
//		try {
//			Session session = SecurityUtils.getSubject().getSession(false);
//
//			return session == null ? null : session
//					.getAttribute(CommonConstants.CURRENT_USER);
//		} catch (UnknownSessionException e) {
//			logger.warn("Unknown Session Exception. Maybe it was accessed by device.");
//		}
//		return null;
//	}
//
//	public static String getCurrentUserName() {
//
//		try {
//			Session session = SecurityUtils.getSubject().getSession(false);
//
//			return session == null ? null : (String) session
//					.getAttribute(CommonConstants.CURRENT_USER_NAME);
//		} catch (UnknownSessionException e) {
//			logger.warn("Unknown Session Exception. Maybe it was accessed by device.");
//		}
//
//		return null;
//	}
//
//	public static String getCurrentUserIpAddr() {
//		Subject subject = SecurityUtils.getSubject();
//
//		if (null != subject) {
//			return (String) subject.getSession().getAttribute(
//					CommonConstants.CURRENT_USER_IP_ADDR);
//		}
//
//		return null;
//	}
//
//	public static Long getCurrentUserTypeArea() {
//		Subject subject = SecurityUtils.getSubject();
//
//		if (null != subject) {
//			return (Long) subject.getSession().getAttribute(
//					CommonConstants.CURRENT_USER_TYPE_AREA);
//		}
//
//		return null;
//	}
//
//	public static Locale getCurrentLocale() {
//		Locale locale = Locale.CHINA;
//		Subject subject = SecurityUtils.getSubject();
//
//		if (null != subject && subject.getSession() != null) {
//			Locale cLocale = (Locale) subject.getSession().getAttribute(
//					CommonConstants.CURRENT_LOCALE);
//			locale = cLocale == null ? locale : cLocale;
//		}
//
//		return locale;
//	}

	public static String genUUID() {
		return UUID.randomUUID().toString();
	}

	public static Boolean genBoolean(String bool) {
		if (!isEmpty(bool)) {
			if ("true".equalsIgnoreCase(bool)) {
				return true;
			} else if ("false".equalsIgnoreCase(bool)) {
				return false;
			}
		}
		return null;
	}

	public static String collectiontoString(Collection<String> strings) {
		StringBuffer value = new StringBuffer();
		for (String string : strings) {
			value.append(string).append(",");
		}
		return value.substring(0, value.length() - 1);

	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	/**
	 * 获取字符串长度，中文算2个
	 * 
	 * @param s
	 * @return
	 */
	public static int getWordCount(String s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;

		}
		return length;

	}

	/**
	 * 判断是否为邮箱
	 * 
	 * @param principal
	 * @return
	 */
	public static boolean isEmail(String principal) {
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(principal);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	/**
	 * 判断是否为手机号
	 * 
	 * @param principal
	 * @return
	 */
	public static boolean isPhone(String principal) {
		String check = "^(13[0-9]|15[0-9]|18[0-9]|17[0-9])\\d{8}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(principal);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	/**
	 * 加密手机号的的4-7位
	 * 
	 * @param phone
	 * @return
	 */
	public static String encryptPartPhone(String phone) {
		StringBuilder buffer = new StringBuilder();

		String head = phone.substring(0, 3);
		String tail = phone.substring(7);

		buffer.append(head);
		buffer.append("****");
		buffer.append(tail);
		return buffer.toString();
	}

	/*
	 * 处理邮箱名称，只显示邮箱名前四位 如sqzx_test@test.com处理后为 sqzx*****@test.com
	 */
	public static String processEmailName(String email) {
		StringBuilder buffer = new StringBuilder();
		int atIndex = email.indexOf("@");
		String head = email.substring(0, atIndex);
		String tail = email.substring(atIndex);
		int half = head.length() / 2;

		if (atIndex > 4) {
			buffer.append(head.substring(0, 4));
		} else {
			buffer.append(head.substring(0, half));
		}

		buffer.append("***");
		buffer.append(tail);
		return buffer.toString();
	}

	/**
	 * 获取当前服务器主机名（集群唯一）
	 * 
	 * @return
	 */
	public static String getCurrentServerHostName() {
		if (CommonUtils.isEmpty(serverHostName)) {
			try {
				serverHostName = InetAddress.getLocalHost()
						.getCanonicalHostName();
			} catch (UnknownHostException e) {
				logger.error("Can't get the current server's host name, using random string instead.");
				serverHostName = getRandomString(10);
			}
		}
		return serverHostName;
	}

	/**
	 * 产生指定长度的随机字符串
	 *
	 * @param length
	 *            表示生成字符串的长度
	 * @return
	 */
	public static String getRandomString(int length) {
		return getRandomString("abcdefghijklmnopqrstuvwxyz0123456789!$@#%",
				length);
	}

	public static String getRandomString(String base, int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
