package com.kosherbacon.cryptotrader.backend.backtest;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.kosherbacon.cryptotrader.backend.backtest.Annotations.BacktestStrategy;
import com.kosherbacon.cryptotrader.backend.backtest.Annotations.BacktestTimeSeries;
import com.kosherbacon.cryptotrader.backend.strategy.PrimaryStrategy;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.TimeSeries;

/**
 * Module for the Backtest package.
 */
public class BacktestModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(Strategy.class)
        .annotatedWith(BacktestStrategy.class)
        .to(PrimaryStrategy.class);
  }

  @Provides
  @BacktestTimeSeries
  public TimeSeries provideTimeSeries() {
    return null;
  }
}
