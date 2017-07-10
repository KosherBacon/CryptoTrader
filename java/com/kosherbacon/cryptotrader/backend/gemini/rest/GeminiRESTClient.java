package com.kosherbacon.cryptotrader.backend.gemini.rest;

import com.kosherbacon.cryptotrader.proto.Exchange.ExchangeSymbols;
import com.kosherbacon.cryptotrader.proto.Exchange.ExchangeSymbols.Symbol;
import com.kosherbacon.cryptotrader.proto.Exchange.Ticker;

import java.io.IOException;

public interface GeminiRESTClient {

  /**
   * Gets a list of ExchangeSymbols using the Gemini REST API.
   * @return a list of Symbols (e.g. btcusd).
   * @throws IOException Thrown when a request fails.
   */
  ExchangeSymbols getSymbols() throws IOException;

  Ticker getTicker(Symbol symbol) throws IOException;
}
