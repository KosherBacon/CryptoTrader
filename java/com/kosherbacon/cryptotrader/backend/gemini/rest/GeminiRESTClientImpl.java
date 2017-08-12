package com.kosherbacon.cryptotrader.backend.gemini.rest;

import com.google.inject.Inject;
import com.google.protobuf.util.JsonFormat;
import com.kosherbacon.cryptotrader.backend.gemini.rest.Annotations.GeminiBaseURL;
import com.kosherbacon.cryptotrader.backend.gemini.rest.Annotations.GeminiHttpClient;
import com.kosherbacon.cryptotrader.proto.Exchange.ExchangeSymbols;
import com.kosherbacon.cryptotrader.proto.Exchange.ExchangeSymbols.Symbol;
import com.kosherbacon.cryptotrader.proto.Exchange.Ticker;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class GeminiRESTClientImpl implements GeminiRESTClient {

  private String baseUrl;
  private OkHttpClient okHttpClient;

  @Inject
  public GeminiRESTClientImpl(@GeminiBaseURL String baseUrl,
                              @GeminiHttpClient OkHttpClient okHttpClient) {
    this.baseUrl = baseUrl;
    this.okHttpClient = okHttpClient;
  }

  @Override
  public ExchangeSymbols getSymbols() throws IOException {
    Request request = new Request.Builder()
        .url(baseUrl + "/symbols")
        .build();

    Response response = okHttpClient.newCall(request).execute();
    if (!response.isSuccessful()) {
      throw new IOException("Unexpected code " + response);
    }

    String payload = "{\"symbols\": " + response.body().string() + "}";
    ExchangeSymbols.Builder symbolsBuilder = ExchangeSymbols.newBuilder();
    JsonFormat.parser().merge(payload, symbolsBuilder);
    return symbolsBuilder.build();
  }

  @Override
  public Ticker getTicker(Symbol symbol) throws IOException {
    Request request = new Request.Builder()
        .url(baseUrl + "/pubticker/" + symbol.name())
        .build();

    Response response = okHttpClient.newCall(request).execute();
    if (!response.isSuccessful()) {
      throw new IOException("Unexpected code " + response);
    }

    String payload = response.body().string();
    Ticker.Builder tickerBuilder = Ticker.newBuilder();
    JsonFormat.parser().merge(payload, tickerBuilder);

    return tickerBuilder.build();
  }
}
