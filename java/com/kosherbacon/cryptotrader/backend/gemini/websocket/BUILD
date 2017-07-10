package(default_visibility = ["//visibility:public"])

java_library(
    name = "annotations",
    srcs = ["Annotations.java",],
    deps = [
        "@com_google_inject_guice//jar"
    ],
)

java_library(
    name = "gemini",
    srcs = [
        "GeminiWebSocketService.java",
    ],
    deps = [
        ":annotations",
        "//com/kosherbacon/cryptotrader/proto:exchange_java_proto",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "@gson//jar",
        "@org_java_websocket//jar",
        "@protobuf_java_util//jar",
        "@protobuf_java//jar",
    ],
)

java_library(
    name = "module",
    srcs = [
        "GeminiWebSocketModule.java",
    ],
    deps = [
        ":annotations",
        "//com/kosherbacon/cryptotrader/backend:module",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
    ],
)
