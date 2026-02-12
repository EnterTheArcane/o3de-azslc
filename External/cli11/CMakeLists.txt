include(FetchContent)

set(WITH_DEMO OFF)

FetchContent_Declare(cli11
    URL https://github.com/CLIUtils/CLI11/archive/refs/tags/v2.6.1.tar.gz
    URL_HASH SHA256=377691f3fac2b340f12a2f79f523c780564578ba3d6eaf5238e9f35895d5ba95
    DOWNLOAD_NO_PROGRESS ON
)

FetchContent_MakeAvailable(cli11)

add_library(External::cli11 ALIAS CLI11)
