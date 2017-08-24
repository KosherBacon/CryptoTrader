package(default_visibility = ["//visibility:public"])

java_library(
    name = "annotations",
    srcs = ["Annotations.java",],
    deps = [
        "@com_google_inject_guice//jar"
    ],
)

java_library(
    name = "module",
    srcs = [
        "BacktestModule.java",
    ],
    deps = [
        ":annotations",
        "//com/kosherbacon/cryptotrader/backend/strategy",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "@javax_inject//jar",
        "@aopalliance//jar",
        "@ta4j//jar",
    ]
)

java_binary(
    name = "backtest",
    srcs = [
        "BacktestRunner.java",
    ],
    main_class = "com.kosherbacon.cryptotrader.backend.TradeRunner",
    deps = [
        ":annotations",
        ":backtest_result_java_proto",
        ":module",
        "@aopalliance//jar",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "@javax_inject//jar",
        "@ta4j//jar",
    ],
)

proto_library(
    name = "backtest_result",
    srcs = ["backtest_result.proto",],
)

java_proto_library(
    name = "backtest_result_java_proto",
    deps = [":backtest_result",],
)
