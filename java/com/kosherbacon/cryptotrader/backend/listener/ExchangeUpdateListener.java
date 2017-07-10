package com.kosherbacon.cryptotrader.backend.listener;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.kosherbacon.cryptotrader.backend.gemini.rest.GeminiRESTClient;
import com.kosherbacon.cryptotrader.backend.listener.Annotations.ExecutorService;
import com.kosherbacon.cryptotrader.backend.listener.Annotations.TickSize;
import com.kosherbacon.cryptotrader.proto.Exchange.ExchangeSymbols.Symbol;
import com.kosherbacon.cryptotrader.proto.Exchange.ExchangeUpdate;
import com.kosherbacon.cryptotrader.proto.Exchange.Ticker;
import eu.verdelhan.ta4j.Tick;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExchangeUpdateListener {

  private Tick currentTick;

  @Inject
  public ExchangeUpdateListener(
      EventBus eventBus,
      GeminiRESTClient restClient,
      @ExecutorService ScheduledExecutorService executorService,
      @TickSize long tickLengthMillis) {
    try {
      Ticker ticker = restClient.getTicker(Symbol.btcusd);
      eventBus.register(this);

      if (ticker.hasLast()) {
        double last = ticker.getLast();
        this.currentTick = new Tick(DateTime.now(), last, last, last, last, last);
        executorService.scheduleAtFixedRate(() -> {}, // TODO(KosherBacon): Fire tick events.
            tickLengthMillis, tickLengthMillis, TimeUnit.MILLISECONDS);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Subscribe
  @SuppressWarnings("unused")
  public void exchangeUpdateEvent(ExchangeUpdate update) {
    System.out.println(update);
  }
}
