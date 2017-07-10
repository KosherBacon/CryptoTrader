package com.kosherbacon.cryptotrader.backend;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import com.kosherbacon.cryptotrader.backend.listener.ExchangeUpdateModule;

public class TradeModule extends AbstractModule {

  private static final EventBus TRADE_EVENT_BUS = new EventBus("Default EventBus");

  @Override
  protected void configure() {
    install(new ExchangeUpdateModule());

    bind(EventBus.class).toInstance(TRADE_EVENT_BUS);
    bindListener(Matchers.any(), new TypeListener() {
      public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
        typeEncounter.register((InjectionListener<I>) TRADE_EVENT_BUS::register);
      }
    });
  }
}
