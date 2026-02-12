cmake_minimum_required(VERSION 3.25)
cmake_policy(SET CMP0135 NEW)

project(azslc)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>")
set(CMAKE_POLICY_VERSION_MINIMUM "3.5")

set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY $<1:${CMAKE_BINARY_DIR}/lib>)
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY $<1:${CMAKE_BINARY_DIR}/lib>)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY $<1:${CMAKE_BINARY_DIR}/bin>)

add_subdirectory(External/antlr4)
add_subdirectory(External/cli11)
add_subdirectory(External/jsoncpp)

# get all chars that are not separators before the end
string(REGEX MATCH "[^/\\]*$" buildPlatform ${CMAKE_BINARY_DIR})

file(GLOB_RECURSE azslc_PlatformSrc
    Platform/*.cpp
    Platform/*.h
)

file(GLOB_RECURSE azslc_LocalSrc
    Source/*.cpp
    Source/*.h
    Source/*.tpl
)

file(GLOB_RECURSE azslc_ExternalSrc
    Source/external/mpark-variant/master/*.hpp
    Source/external/tiny/*.h
)

file(GLOB_RECURSE azslc_GeneratedSrc
    Source/generated/*.cpp
)

set(ANTLR4CPP_INCLUDE_DIRS
    ${antlr4_SOURCE_DIR}/runtime/Cpp/runtime/src
    ${antlr4_SOURCE_DIR}/runtime/Cpp/runtime/src/dfa
    ${antlr4_SOURCE_DIR}/runtime/Cpp/runtime/src/misc
    ${antlr4_SOURCE_DIR}/runtime/Cpp/runtime/src/tree
)

add_executable(azslc ${azslc_LocalSrc} ${azslc_PlatformSrc} ${azslc_GeneratedSrc} ${azslc_ExternalSrc})

target_include_directories(azslc PRIVATE
    ${ANTLR4CPP_INCLUDE_DIRS}
    Platform/Common/Source
    Source
    Source/external
    Source/external/mpark-variant/master
    Source/external/tiny
)

if(MSVC)
    target_compile_options(azslc PRIVATE
        /MP
        /permissive-
        /Zc:__cplusplus
        /utf-8
    )
    target_compile_definitions(azslc PRIVATE
        _CRT_SECURE_NO_WARNINGS
    )
elseif(UNIX AND NOT APPLE)
    add_compile_options(
        -Wno-language-extension-token
    )
endif()

target_link_libraries(azslc PRIVATE
    antlr4_static
    CLI11::CLI11
    jsoncpp_lib_static
)

set_property(
    DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}
    PROPERTY VS_STARTUP_PROJECT azslc
)
