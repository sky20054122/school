/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */
package com.brxy.school.webcoket.handler;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * 
 * @author Eric
 *
 */
public class DeviceHandshakeInterceptor extends HttpSessionHandshakeInterceptor
{

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
            ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception
    {
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
            ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception)
    {
        super.afterHandshake(request, response, wsHandler, exception);
    }
    

}
