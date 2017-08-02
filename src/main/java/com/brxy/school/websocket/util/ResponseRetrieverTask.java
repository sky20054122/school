/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */

package com.brxy.school.websocket.util;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brxy.school.common.WSPushStatus;
import com.brxy.school.service.domain.ResponseResult;
import com.brxy.school.util.CommonUtils;
import com.brxy.school.webcoket.service.WSResponseService;

/**
 * @author Eric
 * 
 */
public class ResponseRetrieverTask implements Callable<ResponseResult> {
	private static final int RETRY_TIMES = 6;

	private static Logger logger = LoggerFactory
			.getLogger(ResponseRetrieverTask.class);

	// 命令是否成功下发到设备
	private WSPushStatus isNotified;

	// 请求ID
	private String requestId;

	public ResponseRetrieverTask(WSPushStatus isNotified, String requestId) {
		this.isNotified = isNotified;
		this.requestId = requestId;
	}

	@Override
	public ResponseResult call() throws Exception {
		WSResponseService resService = CommonUtils
				.getBean(WSResponseService.class);
		ResponseResult responseResult = null;
		if (isNotified != WSPushStatus.SEND_FAILED) {
			int times = 0;
			while (times < RETRY_TIMES) {
				logger.debug(
						"Try "
								+ (times + 1)
								+ " times to get the response result from cache. requestId={}",
						requestId);
				Thread.sleep(1000);
				responseResult = (ResponseResult) resService
						.getResult(requestId);

				if (responseResult != null) {
					logger.debug("Got the response result:" + responseResult);
					break;
				}

				times++;
			}

		}
		return responseResult;
	}
}
