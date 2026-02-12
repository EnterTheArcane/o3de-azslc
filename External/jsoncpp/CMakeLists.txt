include(FetchContent)

FetchContent_Declare(jsoncpp
    URL https://github.com/open-source-parsers/jsoncpp/archive/24682e37bf1425455b45e7db69dbdbcd30aec7dd.tar.gz
    URL_HASH SHA256=2c542011d72ff5bdd582c6d69b595b7256ddbaa7ea46556374ee537aa16a18c7 # v1.6.2
)

FetchContent_MakeAvailable(jsoncpp)
