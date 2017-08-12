package com.kosherbacon.cryptotrader.backend.strategy;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.kosherbacon.cryptotrader.backend.strategy.Annotations.MaxTickCount;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;

import javax.inject.Inject;

public class PrimaryStrategy {

  private final TimeSeries movingTimeSeries;

  @Inject
  PrimaryStrategy(EventBus eventBus,
                  @MaxTickCount int maxTickCount,
                  TimeSeries movingTimeSeries) {
    eventBus.register(this);
    this.movingTimeSeries = movingTimeSeries;
    this.movingTimeSeries.setMaximumTickCount(maxTickCount);
  }

  @Subscribe
  @SuppressWarnings("unused")
  private void newTick(Tick newTick) {
    movingTimeSeries.addTick(newTick);
  }

}
