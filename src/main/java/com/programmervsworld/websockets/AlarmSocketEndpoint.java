package com.programmervsworld.websockets;

import java.io.IOException;
import java.util.Locale;

import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlarmSocketEndpoint extends Endpoint {

  @Override
  public void onOpen(Session session, EndpointConfig endpoint) {
    session.setMaxIdleTimeout(-1);
    session.addMessageHandler(new MessageHandler.Whole<String>() {

            @Override
            public void onMessage(String name) {
                try {

                    if("ping".equals(name.toLowerCase())){
                      session.getBasicRemote().sendText("pong");
                      return;
                    }
                    
                    session.getOpenSessions().stream().forEach(s -> {
                      try {
                        s.getBasicRemote().sendText(name);
                      } catch (IOException e) {
                        log.error("Something happened in the web socket handler", e);
                      }
                    });
                    log.info("Got socket with "+name);
                } catch (IOException ex) {
                    log.error("Somthing happened in the web socket handler", ex);
                }
            }
        });
  }

}