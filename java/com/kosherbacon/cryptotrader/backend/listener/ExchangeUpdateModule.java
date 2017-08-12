package com.kosherbacon.cryptotrader.backend.listener;

import com.google.inject.AbstractModule;
import com.kosherbacon.cryptotrader.backend.gemini.rest.GeminiRESTModule;
import com.kosherbacon.cryptotrader.backend.listener.Annotations.ExecutorService;
import com.kosherbacon.cryptotrader.backend.listener.Annotations.TickSize;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExchangeUpdateModule extends AbstractModule {

  private static final long tickLengthMillis = 60 * 1000L;

  @Override
  protected void configure() {
    install(new GeminiRESTModule());

    bindConstant().annotatedWith(TickSize.class).to(tickLengthMillis);
    bind(ScheduledExecutorService.class)
        .annotatedWith(ExecutorService.class)
        .toInstance(Executors.newSingleThreadScheduledExecutor());
  }
}
