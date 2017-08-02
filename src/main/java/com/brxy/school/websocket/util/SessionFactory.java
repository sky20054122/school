/**
 * 
 */
package com.brxy.school.websocket.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.brxy.school.common.FirmVersion;

/**
 * @author Eric
 *
 */
public class SessionFactory {

	private static SessionFactory sessionFactory = new SessionFactory();

	private static Map<FirmVersion, ConcurrentHashMap<String, Object>> sessionMaps = new HashMap<>();

	
	private SessionFactory() {
	}

	public static SessionFactory getInstance() {
		return sessionFactory;
	}

	public ConcurrentHashMap<String, Object> getSessionMap(
			FirmVersion subVersion) {
		ConcurrentHashMap<String, Object> sessionMap = sessionMaps
				.get(subVersion);

		if (null == sessionMap) {
			sessionMap = new ConcurrentHashMap<String, Object>();
			sessionMaps.put(subVersion, sessionMap);
		}

		return sessionMap;
	}
}
