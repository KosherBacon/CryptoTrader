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
        "StrategyModule.java",
    ],
    deps = [
        ":annotations",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "@javax_inject//jar",
        "@aopalliance//jar",
        "@ta4j//jar",
    ]
)

java_library(
    name = "strategy",
    srcs = [
        "PrimaryStrategy.java",
    ],
    deps = [
        ":annotations",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "@javax_inject//jar",
        "@ta4j//jar",
    ],
)