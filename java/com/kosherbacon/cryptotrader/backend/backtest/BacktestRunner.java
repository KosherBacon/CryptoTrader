package com.kosherbacon.cryptotrader.backend.backtest;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kosherbacon.cryptotrader.backend.backtest.Annotations.BacktestStrategy;
import com.kosherbacon.cryptotrader.backend.backtest.Annotations.BacktestTimeSeries;
import com.kosherbacon.cryptotrader.backend.backtest.Backtest.BacktestResult;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.TimeSeries;

import javax.inject.Inject;

/**
 * Class to run a backtest.
 */
public class BacktestRunner {

  private final TimeSeries timeSeries;
  private final Strategy strategy;

  @Inject
  public BacktestRunner(@BacktestTimeSeries TimeSeries timeSeries,
                        @BacktestStrategy Strategy strategy) {
    this.timeSeries = timeSeries;
    this.strategy = strategy;
  }

  public BacktestResult performBacktest() {
    BacktestResult.Builder builder = BacktestResult.newBuilder();
    return builder.build();
  }

  public static void main(String args[]) {
    Injector injector = Guice.createInjector(new BacktestModule());
    BacktestRunner backtestRunner = injector.getInstance(BacktestRunner.class);
  }
}
