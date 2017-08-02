package com.brxy.school.webcoket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.brxy.school.webcoket.handler.ConnectionHandler;
import com.brxy.school.webcoket.handler.DeviceHandshakeInterceptor;

/**
*
*@author xiaobing
*@version 2016年6月2日 上午11:03:03
*/

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

	/**
	 * 创建WebSocket容器
	 * @return
	 */
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer()
    {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(WebSocketConstant.MAX_TEXT_MESSAGE_BUFFER_SIZE);
        container.setMaxBinaryMessageBufferSize(WebSocketConstant.MAX_BINARY_MESSAGE_BUFFER_SIZE);
        container.setMaxSessionIdleTimeout(WebSocketConstant.MAX_SESSION_IDEL_TIMEOUT);
        return container;
    }

    /**
     * 注册Handlers
     */
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
    {
        registry.addHandler(getConnectionHandler(),
                WebSocketConstant.CONNECTION_HANDLER_DTS_URL).addInterceptors(
                getHandshakeInterceptor());
        
//        registry.addHandler(getConnectionHandler(),"/websocket/connHandler").addInterceptors(
//                getHandshakeInterceptor());
    }

    @Bean
    public HandshakeInterceptor getHandshakeInterceptor()
    {
        return new DeviceHandshakeInterceptor();
    }

    @Bean
    public WebSocketHandler getConnectionHandler()
    {
        // 为每个session创建一个handler
        return new PerConnectionWebSocketHandler(ConnectionHandler.class);
    }

}
