cmake_minimum_required(VERSION 3.24)
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

# get all chars that are not separators before the end
string(REGEX MATCH "[^/\\]*$" buildPlatform ${CMAKE_BINARY_DIR})

file(GLOB_RECURSE azslc_PlatformSrc
    Platform/*.cpp
    Platform/*.h
)

file(GLOB_RECURSE azslc_LocalSrc
    Source/*.cpp
    Source/*.tpl
    Source/*.h
)

file(GLOB_RECURSE azslc_ExternalSrc
    Source/external/jsoncpp/dist/json/*.h
    Source/external/jsoncpp/dist/*.cpp
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

source_group(azslc FILES ${azslc_LocalSrc})
source_group(external FILES ${azslc_ExternalSrc})
source_group(generated FILES ${azslc_GeneratedSrc})
source_group(platforms FILES ${azslc_PlatformSrc})

if(MSVC)
    target_compile_options(azslc PRIVATE
        /MP
        /permissive-
        /Zc:__cplusplus
        /utf-8
    )
    target_compile_definitions(azslc PRIVATE
        _CRT_SECURE_NO_WARNINGS
        _SILENCE_CXX17_CODECVT_HEADER_DEPRECATION_WARNING
        ANTLR4CPP_STATIC
    )
elseif(UNIX AND NOT APPLE)
    target_link_libraries(azslc PRIVATE stdc++fs)
endif()

target_link_libraries(azslc PRIVATE CLI11::CLI11 antlr4_static)

set_property(GLOBAL PROPERTY USE_FOLDERS ON)

set_target_properties(azslc PROPERTIES
    EXECUTABLE_OUTPUT_PATH ${CMAKE_BINARY_DIR}
    LIBRARY_OUTPUT_PATH ${CMAKE_BINARY_DIR}
)

set_property(DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR} PROPERTY VS_STARTUP_PROJECT azslc)
