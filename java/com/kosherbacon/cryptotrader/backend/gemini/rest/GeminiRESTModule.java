package com.kosherbacon.cryptotrader.backend.gemini.rest;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.kosherbacon.cryptotrader.backend.gemini.rest.Annotations.GeminiBaseURL;
import com.kosherbacon.cryptotrader.backend.gemini.rest.Annotations.GeminiHttpClient;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class GeminiRESTModule extends AbstractModule {

  private static final long restConnectionTimeout = 250L; // 250 ms

  private static final long restReadTimeout = 250L; // 250 ms

  private static final long restWriteTimeout = 250L; // 250 ms

  @Override
  protected void configure() {
    bind(GeminiRESTClient.class).to(GeminiRESTClientImpl.class);
  }

  @Provides
  @GeminiBaseURL
  @SuppressWarnings("unused")
  String provideGeminiBaseURL() {
    return "https://api.gemini.com/v1";
  }

  @Provides
  @GeminiHttpClient
  @SuppressWarnings("unused")
  OkHttpClient provideOkHttpClient() {
    return new OkHttpClient.Builder()
        .connectTimeout(restConnectionTimeout, TimeUnit.MILLISECONDS)
        .readTimeout(restReadTimeout, TimeUnit.MILLISECONDS)
        .writeTimeout(restWriteTimeout, TimeUnit.MILLISECONDS)
        .build();
  }
}
