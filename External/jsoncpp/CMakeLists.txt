include(FetchContent)

set(JSONCPP_WITH_TESTS OFF CACHE BOOL "")

FetchContent_Declare(jsoncpp
    URL https://github.com/open-source-parsers/jsoncpp/archive/refs/tags/1.9.6.tar.gz
    URL_HASH SHA256=f93b6dd7ce796b13d02c108bc9f79812245a82e577581c4c9aabe57075c90ea2
    DOWNLOAD_NO_PROGRESS ON
)

FetchContent_MakeAvailable(jsoncpp)

add_library(External::jsoncpp ALIAS jsoncpp_static)
