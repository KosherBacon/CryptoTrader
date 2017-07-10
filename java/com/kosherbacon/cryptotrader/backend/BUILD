package(default_visibility = ["//visibility:public"])

java_binary(
    name = "trader",
    srcs = [
      "TradeRunner.java",
    ],
    main_class = "com.kosherbacon.cryptotrader.backend.TradeRunner",
    deps = [
        ":module",
        "//com/kosherbacon/cryptotrader/backend/listener:listener",
        "//com/kosherbacon/cryptotrader/backend/gemini/websocket:gemini",
        "//com/kosherbacon/cryptotrader/backend/gemini/websocket:module",
        "//com/kosherbacon/cryptotrader/proto:exchange_java_proto",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "@javax_inject//jar",
        "@aopalliance//jar",
        
        
        "//com/kosherbacon/cryptotrader/backend/gemini/rest:module",
        "//com/kosherbacon/cryptotrader/backend/gemini/rest:rest",
    ],
)

java_library(
    name = "module",
    srcs = [
        "TradeModule.java",
    ],
    deps = [
        "//com/kosherbacon/cryptotrader/backend/gemini/rest:module",
        "//com/kosherbacon/cryptotrader/backend/listener:module",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
    ]
)
