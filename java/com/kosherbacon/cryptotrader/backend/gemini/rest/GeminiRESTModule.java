package com.kosherbacon.cryptotrader.backend.gemini.rest;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.kosherbacon.cryptotrader.backend.gemini.rest.Annotations.GeminiBaseURL;

public class GeminiRESTModule extends AbstractModule {

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
}
