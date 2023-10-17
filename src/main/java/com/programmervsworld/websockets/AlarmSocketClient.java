package com.programmervsworld.websockets;

import java.io.IOException;
import java.net.URI;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ClientEndpoint
public class AlarmSocketClient {
  Session session = null;
  private MessageHandler handler;

  public AlarmSocketClient(URI endpointURI) {
    try {
      WebSocketContainer container = ContainerProvider.getWebSocketContainer();
      container.connectToServer(this, endpointURI);
    } catch (Exception ex) {
      log.error("Couldn't open connection to client socket on server", ex);
      throw new RuntimeException(ex);
    }
  }

  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    try {
      session.getBasicRemote().sendText("Opening connection");
    } catch (IOException ex) {
      System.out.println(ex);
    }
  }

  public void addMessageHandler(MessageHandler msgHandler) {
    this.handler = msgHandler;
  }

  @OnMessage
  public void processMessage(String message) {
    System.out.println("Received message in client: " + message);
  }

  public void sendMessage(String message) {
    try {
      this.session.getBasicRemote().sendText(message);
    } catch (IOException ex) {
      log.error("Failed to send message", ex);
    }
  }

  public static interface MessageHandler {

    public void handleMessage(String message);
  }
}
