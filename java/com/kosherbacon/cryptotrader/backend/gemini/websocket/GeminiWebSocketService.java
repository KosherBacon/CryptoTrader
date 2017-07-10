package com.kosherbacon.cryptotrader.backend.gemini.websocket;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.kosherbacon.cryptotrader.backend.gemini.websocket.Annotations.GeminiURL;
import com.kosherbacon.cryptotrader.proto.Exchange.ExchangeEvent;
import com.kosherbacon.cryptotrader.proto.Exchange.ExchangeType;
import com.kosherbacon.cryptotrader.proto.Exchange.ExchangeUpdate;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class GeminiWebSocketService extends WebSocketClient {

  private static final Logger LOGGER = Logger.getLogger(GeminiWebSocketService.class.getName());

  private final EventBus eventBus;

  @Inject
  public GeminiWebSocketService(@GeminiURL URI uri, EventBus eventBus)
      throws KeyManagementException, NoSuchAlgorithmException, IOException {
    super(uri, new Draft_6455());
    this.eventBus = eventBus;

    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(null, null, null);
    SSLSocketFactory factory = sslContext.getSocketFactory();
    this.setSocket(factory.createSocket());
  }

  @Override
  public void onOpen(ServerHandshake serverHandshake) {
    LOGGER.info("Connected to Gemini!");
  }

  @Override
  public void onMessage(String message) {
    try {
      ExchangeUpdate.Builder updateBuilder = ExchangeUpdate.newBuilder();
      JsonFormat.parser().merge(message, updateBuilder);
      ExchangeUpdate update = updateBuilder.build();

      boolean isTrade = update.getEventsList()
          .stream()
          .map(ExchangeEvent::getType)
          .anyMatch(type -> type.equals(ExchangeType.Type.trade));
      if (isTrade) {
        eventBus.post(updateBuilder.build());
      }
    } catch (InvalidProtocolBufferException e) {
      LOGGER.severe(String.format("Failed to unpack message: %s\n%s", message, e.toString()));
    }
  }

  @Override
  public void onClose(int i, String reason, boolean remote) {
    LOGGER.severe(String.format("%s closed Gemini connection with reason: %s",
        remote ? "Remote" : "We", reason));
  }

  @Override
  public void onError(Exception e) {
    LOGGER.severe(String.format("Gemini connection error:\n%s", e.toString()));
  }
}
