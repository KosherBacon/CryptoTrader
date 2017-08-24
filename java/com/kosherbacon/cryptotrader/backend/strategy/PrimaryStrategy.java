package com.kosherbacon.cryptotrader.backend.strategy;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.kosherbacon.cryptotrader.backend.strategy.Annotations.MaxTickCount;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Rule;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.oscillators.CMOIndicator;
import eu.verdelhan.ta4j.indicators.oscillators.StochasticOscillatorKIndicator;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.trackers.EMAIndicator;
import eu.verdelhan.ta4j.indicators.trackers.MACDIndicator;
import eu.verdelhan.ta4j.indicators.trackers.SMAIndicator;
import eu.verdelhan.ta4j.trading.rules.*;

import javax.inject.Inject;

public class PrimaryStrategy extends Strategy {

  /**
   * The number of ticks needed for the strategy to be fully functional.
   */
  public static final int TICKS_NEEDED = 205;

  /**
   * The number of periods to use for the Chande Momentum Oscillator.
   */
  private static final int CMO_PERIODS = 2;

  /**
   * The upper bound to use for the Chande Momentum Oscillator
   */
  private static final int CMO_UPPER = 70;

  /**
   * The lower bound to use for the Chande Momentum Oscillator
   */
  private static final int CMO_LOWER = -70;

  /**
   * The number of periods to use for the short running part of the MACD.
   */
  private static final int MACD_SHORT_PERIODS = 9;

  /**
   * The number of periods to use for the long running part of the MACD.
   */
  private static final int MACD_LONG_PERIODS = 26;

  /**
   * The number of periods to use for the short running SMA.
   */
  private static final int SMA_SHORT_PERIODS = 5;

  /**
   * The number of periods to use for the long running SMA.
   */
  private static final int SMA_LONG_PERIODS = 200;

  /**
   * The threshold (in percent) for the maximum loss allowed on a given
   * trade. This value protects against severe losses, lower values should be
   * used for more conservative trading.
   */
  private static final int STOP_LOSS_THRESHOLD = 10;

  /**
   * The threshold (in percent) for the maximum gain allowed on a given
   * trade. This value protects the gains from a trade, lower values should
   * be used for more conservative trading.
   */
  private static final int STOP_GAIN_THRESHOLD = 10;

  private final TimeSeries movingTimeSeries;

  @Inject
  PrimaryStrategy(EventBus eventBus,
                  @MaxTickCount int maxTickCount,
                  TimeSeries movingTimeSeries) {
    super(buildEntryRule(movingTimeSeries), buildExitRule(movingTimeSeries));
    eventBus.register(this);
    this.movingTimeSeries = movingTimeSeries;
    this.movingTimeSeries.setMaximumTickCount(maxTickCount);
  }

  @Subscribe
  @SuppressWarnings("unused")
  private void newTick(Tick newTick) {
    movingTimeSeries.addTick(newTick);
  }

  private static Rule buildEntryRule(TimeSeries series) {
    ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

    SMAIndicator shortSma = new SMAIndicator(closePrice, SMA_SHORT_PERIODS);
    SMAIndicator longSma = new SMAIndicator(closePrice, SMA_LONG_PERIODS);

    CMOIndicator cmo = new CMOIndicator(closePrice, CMO_PERIODS);

    // The bias is bearish when the shorter-moving averagemoves below the
    // longer moving average.
    EMAIndicator shortEma = new EMAIndicator(closePrice, 9);
    EMAIndicator longEma = new EMAIndicator(closePrice, 26);

    StochasticOscillatorKIndicator stochasticOscillK = new
        StochasticOscillatorKIndicator(series, 14);

    MACDIndicator macd = new MACDIndicator(closePrice, MACD_SHORT_PERIODS,
        MACD_LONG_PERIODS);
    EMAIndicator emaMacd = new EMAIndicator(macd, 18);

    Rule momentumEntry = new OverIndicatorRule(shortSma, longSma) // Trend
        // Signal 1
        .and(new CrossedDownIndicatorRule(cmo,
            Decimal.valueOf(CMO_LOWER)))
        // Signal 2
        .and(new OverIndicatorRule(shortEma, closePrice));

    Rule entryRule = new OverIndicatorRule(shortEma, longEma) // Trend
        // Signal 1
        .and(new CrossedDownIndicatorRule(stochasticOscillK,
            Decimal.valueOf(20)))
        // Signal 2
        .and(new OverIndicatorRule(macd, emaMacd))
        .or(momentumEntry);
    return entryRule;
  }

  private static Rule buildExitRule(TimeSeries series) {
    ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

    SMAIndicator shortSma = new SMAIndicator(closePrice, SMA_SHORT_PERIODS);
    SMAIndicator longSma = new SMAIndicator(closePrice, SMA_LONG_PERIODS);

    CMOIndicator cmo = new CMOIndicator(closePrice, CMO_PERIODS);

    // The bias is bearish when the shorter-moving averagemoves below the
    // longer moving average.
    EMAIndicator shortEma = new EMAIndicator(closePrice, 9);
    EMAIndicator longEma = new EMAIndicator(closePrice, 26);

    StochasticOscillatorKIndicator stochasticOscillK = new
        StochasticOscillatorKIndicator(series, 14);

    MACDIndicator macd = new MACDIndicator(closePrice, MACD_SHORT_PERIODS,
        MACD_LONG_PERIODS);
    EMAIndicator emaMacd = new EMAIndicator(macd, 18);

    Rule momentumExit = new UnderIndicatorRule(shortSma, longSma) // Trend
        // Signal 1
        .and(new CrossedUpIndicatorRule(cmo,
            Decimal.valueOf(CMO_UPPER)))
        // Signal 2
        .and(new UnderIndicatorRule(shortSma, closePrice));

    // Exit rule
    Rule exitRule = new UnderIndicatorRule(shortEma, longEma) // Trend
        // Signal 1
        .and(new CrossedUpIndicatorRule(stochasticOscillK, Decimal.valueOf(80)))
        // Signal 2
        .and(new UnderIndicatorRule(macd, emaMacd))
        .or(momentumExit)
        // Protect against severe losses
        .or(new StopLossRule(closePrice, Decimal.valueOf(STOP_LOSS_THRESHOLD)))
        // Take profits and run
        .or(new StopGainRule(closePrice, Decimal.valueOf(STOP_GAIN_THRESHOLD)));

    return exitRule;
  }
}
