package com.kosherbacon.cryptotrader.backend.listener;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.kosherbacon.cryptotrader.backend.gemini.rest.GeminiRESTClient;
import com.kosherbacon.cryptotrader.backend.listener.Annotations.ExecutorService;
import com.kosherbacon.cryptotrader.backend.listener.Annotations.TickSize;
import com.kosherbacon.cryptotrader.proto.Exchange.ExchangeSymbols.Symbol;
import com.kosherbacon.cryptotrader.proto.Exchange.ExchangeType;
import com.kosherbacon.cryptotrader.proto.Exchange.ExchangeUpdate;
import com.kosherbacon.cryptotrader.proto.Exchange.Ticker;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Tick;
import org.joda.time.DateTime;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExchangeUpdateListener {

  private EventBus eventBus;
  private Tick currentTick;
  private DateTime currentTime;
  private long tickLengthMillis;

  @Inject
  public ExchangeUpdateListener(
      EventBus eventBus,
      GeminiRESTClient restClient,
      @ExecutorService ScheduledExecutorService executorService,
      @TickSize long tickLengthMillis) {
    this.eventBus = eventBus;
    this.tickLengthMillis = tickLengthMillis;
    this.currentTime = DateTime.now();

    try {
      Ticker ticker = restClient.getTicker(Symbol.btcusd);
      eventBus.register(this);

      if (ticker.hasLast()) {
        double last = ticker.getLast();
        this.currentTick = new Tick(ZonedDateTime.now().plusSeconds(tickLengthMillis / 1000),
            last, last, last, last, last);
        executorService.scheduleAtFixedRate(this::fireTick,
            tickLengthMillis, tickLengthMillis, TimeUnit.MILLISECONDS);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void fireTick() {
    eventBus.post(currentTick);
    currentTime = currentTime.plusMillis(Math.toIntExact(tickLengthMillis));
    Decimal lastClose = currentTick.getClosePrice();
    currentTick = new Tick(ZonedDateTime.now().plusSeconds(tickLengthMillis / 1000),
        lastClose, lastClose, lastClose, lastClose, lastClose);
  }

  @Subscribe
  @SuppressWarnings("unused")
  public void exchangeUpdateEvent(ExchangeUpdate update) {
    if (update.getEventsList().size() > 0
        && update.getEvents(0).getType() == ExchangeType.Type.trade
        && update.getEvents(0).hasAmount()
        && update.getEvents(0).hasPrice()) {
      currentTick.addTrade(update.getEvents(0).getAmount(), update.getEvents(0).getPrice());
      System.out.println(update);
    }
  }
}
