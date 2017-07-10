package(default_visibility = ["//visibility:public"])

java_library(
    name = "annotations",
    srcs = ["Annotations.java",],
    deps = [
        "@com_google_inject_guice//jar"
    ],
)

java_library(
    name = "listener",
    srcs = [
        "ExchangeUpdateListener.java",
    ],
    deps = [
        ":annotations",
        "//com/kosherbacon/cryptotrader/backend/gemini/rest:rest",
        "//com/kosherbacon/cryptotrader/proto:exchange_java_proto",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "@javax_inject//jar",
        "@joda_time//jar",
        "@ta4j//jar",
    ],
)

java_library(
    name = "module",
    srcs = [
        "ExchangeUpdateModule.java",
    ],
    deps = [
        ":annotations",
        "//com/kosherbacon/cryptotrader/backend/gemini/rest:module",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
    ],
)
