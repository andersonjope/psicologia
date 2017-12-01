package br.com.jope.psicologia.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebSocketMessageBroker
//@EnableWebSocket
public class WebSocketConfig /*extends AbstractWebSocketMessageBrokerConfigurer*/ {

	/*@Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/");
        config.setApplicationDestinationPrefixes("/app");
    }
 
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//         registry.addEndpoint("/chat");
         registry.addEndpoint("/chat").withSockJS();
    }*/

}
