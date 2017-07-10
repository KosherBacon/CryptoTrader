package com.kosherbacon.cryptotrader.backend.gemini.websocket;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.kosherbacon.cryptotrader.backend.TradeModule;
import com.kosherbacon.cryptotrader.backend.gemini.websocket.Annotations.GeminiURL;

import java.net.URI;
import java.net.URISyntaxException;

public class GeminiWebSocketModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new TradeModule());
  }

  @Provides
  @GeminiURL
  @SuppressWarnings("unused")
  URI provideGeminiURL() throws URISyntaxException {
    return new URI("wss://api.gemini.com/v1/marketdata/BTCUSD");
  }
}
