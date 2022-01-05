package com.ertogrul.omsb2b.web.security.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebsocketSecurityConfig  extends AbstractSecurityWebSocketMessageBrokerConfigurer {


    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages.nullDestMatcher().authenticated().
                simpDestMatchers("/wss/activity").authenticated(). //any user authenticated can send message to /wss/activity
                simpSubscribeDestMatchers("/topic/tracker").hasAuthority("ROLE_SUPERADMIN"). //only ROLE_SUPERADMIN can track other user's activity
                simpTypeMatchers(SimpMessageType.MESSAGE,SimpMessageType.SUBSCRIBE).denyAll()
                .anyMessage().denyAll();
    }

    //this disables csrf token  for websocket  connection
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
