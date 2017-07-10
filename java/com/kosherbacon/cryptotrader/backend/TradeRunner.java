package com.kosherbacon.cryptotrader.backend;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kosherbacon.cryptotrader.backend.gemini.rest.GeminiRESTModule;
import com.kosherbacon.cryptotrader.backend.gemini.websocket.GeminiWebSocketService;
import com.kosherbacon.cryptotrader.backend.listener.ExchangeUpdateListener;
import com.kosherbacon.cryptotrader.backend.gemini.rest.GeminiRESTClient;
import com.kosherbacon.cryptotrader.backend.gemini.websocket.GeminiWebSocketModule;

public class TradeRunner {

  public static void main(String args[]) throws Exception {
    Injector injector = Guice.createInjector(new GeminiWebSocketModule());
    GeminiWebSocketService gws = injector.getInstance(GeminiWebSocketService.class);
    injector = Guice.createInjector(new TradeModule());
    injector.getInstance(ExchangeUpdateListener.class);

    gws.connect();

    Injector inj = Guice.createInjector(new GeminiRESTModule());
    GeminiRESTClient restClient = inj.getInstance(GeminiRESTClient.class);
//    restClient.getTicker(Exchange.ExchangeSymbols.Symbol.btcusd);
  }
}
