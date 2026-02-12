include(FetchContent)

FetchContent_Declare(cli11
    URL https://github.com/CLIUtils/CLI11/archive/3ce61a169297f5fe7d4b08bdbfa82290950d6f7f.tar.gz
    URL_HASH SHA256=4c34eab309be04c1b78be0c3f66c16f204c7a45f5cbdcbe1cab7b69c186e8456
)

FetchContent_MakeAvailable(cli11)
