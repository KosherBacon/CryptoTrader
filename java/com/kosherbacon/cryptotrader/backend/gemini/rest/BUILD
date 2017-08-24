package(default_visibility = ["//visibility:public"])

java_library(
    name = "annotations",
    srcs = ["Annotations.java",],
    deps = [
        "@com_google_inject_guice//jar"
    ],
)

java_library(
    name = "rest",
    srcs = ["GeminiRESTClient.java"],
    deps = [
        "//com/kosherbacon/cryptotrader/proto:exchange_java_proto",
    ],
)

java_library(
    name = "impl",
    srcs = ["GeminiRESTClientImpl.java"],
    deps = [
        ":annotations",
        ":rest",
        "//com/kosherbacon/cryptotrader/proto:exchange_java_proto",
        "@com_google_inject_guice//jar",
        "@okhttp3//jar",
        "@okio//jar",
        
        
        "@gson//jar",
        "@protobuf_java_util//jar",
        "@protobuf_java//jar",
    ],
)

java_library(
    name = "module",
    srcs = ["GeminiRESTModule.java",],
    deps = [
        ":annotations",
        ":impl",
        ":rest",
        "@com_google_inject_guice//jar",
        "@okhttp3//jar",
    ],
)