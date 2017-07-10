package(default_visibility = ["//visibility:public"])

proto_library(
    name = "exchange",
    srcs = ["exchange.proto",],
)

java_proto_library(
    name = "exchange_java_proto",
    deps = [":exchange",],
)

proto_library(
    name = "market",
    srcs = ["market.proto",],
)

java_proto_library(
    name = "market_java_proto",
    deps = [":market",],
)
