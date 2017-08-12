package com.kosherbacon.cryptotrader.backend.strategy;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.kosherbacon.cryptotrader.backend.strategy.Annotations.EmptyTimeSeries;
import com.kosherbacon.cryptotrader.backend.strategy.Annotations.MaxTickCount;
import eu.verdelhan.ta4j.TimeSeries;

/**
 * Module for the Strategy package.
 */
public class StrategyModule extends AbstractModule {

  private static final int maxTickCount = 300;

  @Override
  protected void configure() {
    bindConstant().annotatedWith(MaxTickCount.class).to(maxTickCount);
  }

  @Provides
  @EmptyTimeSeries
  @SuppressWarnings("unused")
  TimeSeries provideEmptyTimeSeries() {
    return new TimeSeries("Main Time Series");
  }
}
