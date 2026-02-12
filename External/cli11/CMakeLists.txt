include(FetchContent)

FetchContent_Declare(cli11
    URL https://github.com/CLIUtils/CLI11/archive/70f8072f9dd2292fd0b9f9e5f58e279f60483ed3.tar.gz
    URL_HASH SHA256=083011ab508b66670d371b1864c22ce39282efa2f5f2ddf78827412530cceaf9 # v2.1.2
)

FetchContent_MakeAvailable(cli11)

add_library(External::cli11 ALIAS CLI11)
