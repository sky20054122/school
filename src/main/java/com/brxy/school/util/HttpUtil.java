/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */

package com.brxy.school.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brxy.school.common.CommonConstants;



/**
 * @author Eric
 * 
 */
public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	private static HttpClient httpClient;

	public static String get(String url) {
		initHttpClient();
		String content = null;
		HttpMethod method = new GetMethod(url);

		try {
			httpClient.executeMethod(method);

			if (method.getStatusLine().getStatusCode() == 200) {
				content = method.getResponseBodyAsString();
			}
		} catch (Exception e) {
			logger.error("Error happend when access the url:" + url, e);
		} finally {
			method.releaseConnection();
		}
		return content;
	}

	public static String post(String url, String body, String contentType,
			String domain) {
		initHttpClient();
		logger.debug("post request for url=" + url + ", body=" + body);

		String returnContent = null;
		httpClient.getHostConfiguration().setHost(domain,
				CommonConstants.DEFAULT_HTTPS_PORT, CommonConstants.HTTPS);

		PostMethod method = new PostMethod(url);
		try {
			if (null != body && body.length() != 0) {
				method.setRequestEntity(new StringRequestEntity(body,
						contentType, CommonConstants.DEFAULT_ENCODING));
			}
			httpClient.executeMethod(method);

			if (method.getStatusLine().getStatusCode() == 200) {
				logger.debug("The response for request url(" + url + ") is "
						+ method.getResponseBodyAsString());
				returnContent = method.getResponseBodyAsString();
			}
		} catch (Exception e) {
			logger.error("Error happend when access the url:" + url, e);
		} finally {
			method.releaseConnection();
		}
		return returnContent;
	}

	public static String post(String url, Map<String, String> paramsMap) {
		String returnContent = null;
		initHttpClient();
		logger.debug("post request for url=", url);
		NameValuePair[] nameValuePair = new NameValuePair[] {};

		if (paramsMap != null) {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> param : paramsMap.entrySet()) {
				NameValuePair pair;
				pair = new NameValuePair(param.getKey(), param.getValue());
				paramList.add(pair);
			}
			nameValuePair = paramList.toArray(nameValuePair);
		}

		PostMethod method = new PostMethod(url);

		try {
			method.setRequestBody(nameValuePair);
			method.getParams().setContentCharset(CommonConstants.DEFAULT_ENCODING);
			httpClient.executeMethod(method);

			if (method.getStatusLine().getStatusCode() == 200) {
				logger.debug("The response for request url(" + url + ") is "
						+ method.getResponseBodyAsString());
				returnContent = method.getResponseBodyAsString();
				logger.debug(returnContent);
			}
		} catch (Exception e) {
			logger.error("Error happend when access the url:" + url, e);
		} finally {
			method.releaseConnection();
		}

		return returnContent;
	}

	private static void initHttpClient() {
//		if (null == httpClient) {
//			httpClient = new HttpClient();
//
//			String proxyDomain = CommonConfiguration
//					.getServerParameterFromEnv(CommonConfiguration.CLOUD_SERVER_OUT_PROXY_DOMAIN);
//			String proxyPort = CommonConfiguration
//					.getServerParameterFromEnv(CommonConfiguration.CLOUD_SERVER_OUT_PROXY_PORT);
//
//			if (!CommonUtils.isEmpty(proxyDomain)
//					&& !CommonUtils.isEmpty(proxyPort)) {
//				httpClient.getHostConfiguration().setProxy(proxyDomain,
//						Integer.parseInt(proxyPort));
//				// httpClient.getHostConfiguration().setProxy("localhost", 808);
//			}
//			httpClient.getParams().setAuthenticationPreemptive(true);
//		}
	}
}
