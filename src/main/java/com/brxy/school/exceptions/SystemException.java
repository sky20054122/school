package com.brxy.school.exceptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;

import com.brxy.school.util.I18Util;

public class SystemException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4555603852638992687L;

//	private static final String SUB_MODULE_KEY = "SUB_MODULE";

	private static SystemExceptionListener listener = null;

	public static SystemException wrap(SystemErrorCode errorCode) {
		return wrap(errorCode, null, null, null);
	}

	public static SystemException wrap(SystemErrorCode errorCode,
			Throwable exception) {
		return wrap(errorCode, null, exception, null);
	}

	public static SystemException wrap(SystemErrorCode errorCode, String message) {
		return wrap(errorCode, message, null, null);
	}

	public static SystemException wrap(SystemErrorCode errorCode,
			String message, Throwable exception) {
		return wrap(errorCode, message, exception, null);
	}

	public static SystemException wrap(SystemErrorCode errorCode,
			String message, String subModule) {
		return wrap(errorCode, message, null, subModule);
	}

	public static SystemException wrap(SystemErrorCode errorCode,
			String message, Throwable exception, String subModule) {
		if (exception instanceof SystemException) {
			SystemException se = (SystemException) exception;
			if (errorCode != null && errorCode != se.getErrorCode()) {
				return new SystemException(message, exception, errorCode);
			}
			return se;
		} else {
			return new SystemException(message, exception, errorCode);
		}
	}

	public static SystemException wrap(Throwable t) {
		return wrap(null, null, t, null);
	}

	public static SystemException wrap(Throwable t, String subModule) {
		return wrap(null, null, t, subModule);
	}

	private SystemErrorCode errorCode;
	private final Map<String, Object> properties = new TreeMap<String, Object>();

	private SystemException(String message, SystemErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
		if (listener != null)
			listener.newExceptionOccure(this);
	}

	private SystemException(String message, Throwable cause,
			SystemErrorCode errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
		if (listener != null)
			listener.newExceptionOccure(this);
	}

	private SystemException(SystemErrorCode errorCode) {
		this.errorCode = errorCode;
		if (listener != null)
			listener.newExceptionOccure(this);
	}

	private SystemException(Throwable cause, SystemErrorCode errorCode) {
		super(cause);
		this.errorCode = errorCode;
		if (listener != null)
			listener.newExceptionOccure(this);
	}

	public String echoStackTrace() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		printStackTrace(new PrintStream(bos));
		try {
			return bos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		return (T) properties.get(name);
	}

	public SystemErrorCode getErrorCode() {
		return errorCode;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

//	public String getCustomMessage() {
//		return I18Util.getMessage(getErrorCode(), getProperties().values()
//				.toArray());
//	}

	public void printStackTrace(PrintStream s) {
		synchronized (s) {
			printStackTrace(new PrintWriter(s));
		}
	}

	public void printStackTrace(PrintWriter s) {
		synchronized (s) {
			s.println(this);
			s.println("\t-------------------------------");
			if (errorCode != null) {
				s.println("\t app module "  
						+ ", errorKey: " + errorCode.getErrorKey());
			}
			for (String key : properties.keySet()) {
				s.println("\t" + key + "=[" + properties.get(key) + "]");
			}
			s.println("\t-------------------------------");
			StackTraceElement[] trace = getStackTrace();
			for (int i = 0; i < trace.length; i++)
				s.println("\tat " + trace[i]);

			Throwable ourCause = getCause();
			if (ourCause != null) {
				ourCause.printStackTrace(s);
			}
			s.flush();
		}
	}

	public SystemException set(String name, Object value) {
		properties.put(name, value);
		return this;
	}

	public SystemException setErrorCode(SystemErrorCode errorCode) {
		this.errorCode = errorCode;
		return this;
	}

	static void setListener(SystemExceptionListener listener) {
		SystemException.listener = listener;
	}
	
	public static void handleException(String errMsg, SystemErrorCode errorCode, Logger logger) {
	    SystemException systemException = SystemException.wrap(
	            errorCode, errMsg);
        logger.error(errMsg, systemException);
        throw systemException;
	}
}
