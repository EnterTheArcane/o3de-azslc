cmake_minimum_required(VERSION 3.24)
cmake_policy(SET CMP0135 NEW) 

project (azslc)

set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>")
set(CMAKE_POLICY_VERSION_MINIMUM "3.5")

include(FetchContent)

FetchContent_Declare(cli11
  URL https://github.com/CLIUtils/CLI11/archive/3ce61a169297f5fe7d4b08bdbfa82290950d6f7f.tar.gz
  URL_HASH SHA256=4c34eab309be04c1b78be0c3f66c16f204c7a45f5cbdcbe1cab7b69c186e8456
)

# Antlr4 external dependency
FetchContent_Declare(antlr4
  URL https://github.com/antlr/antlr4/archive/8e6fd9147b3c9d36b60e2b6656871a55227efb1b.tar.gz
  URL_HASH SHA256=c52fb2a90ae082ab5b5ec6b41f6c0e66f691962e4b4f772580e5da678fe51fed
  SOURCE_SUBDIR runtime/Cpp
)

if(CMAKE_CXX_COMPILER_ID MATCHES "Clang")
  add_compile_options(
    -Wno-language-extension-token
  )
endif()

FetchContent_MakeAvailable(cli11 antlr4)

# get all chars that are not separators before the end
string(REGEX MATCH "[^/\\]*$" buildPlatform ${CMAKE_BINARY_DIR})

file(GLOB_RECURSE  azslc_PlatformSRC
  "${PROJECT_SOURCE_DIR}/../Platform/*.cpp"
  "${PROJECT_SOURCE_DIR}/../Platform/*.h"
)

file(GLOB azslc_LocalSRC
  "${PROJECT_SOURCE_DIR}/*.cpp"
  "${PROJECT_SOURCE_DIR}/*.tpl"
  "${PROJECT_SOURCE_DIR}/*.h"
)

file(GLOB azslc_ExternalSRC
  "${PROJECT_SOURCE_DIR}/external/jsoncpp/dist/json/*.h"
  "${PROJECT_SOURCE_DIR}/external/jsoncpp/dist/*.cpp"
  "${PROJECT_SOURCE_DIR}/external/mpark-variant/master/*.hpp"
  "${PROJECT_SOURCE_DIR}/external/tiny/*.h"
)

file(GLOB azslc_GeneratedSrc
  "${PROJECT_SOURCE_DIR}/generated/*.cpp"
)

set( ANTLR4CPP_INCLUDE_DIRS
  ${antlr4_SOURCE_DIR}/runtime/Cpp/runtime/src
  ${antlr4_SOURCE_DIR}/runtime/Cpp/runtime/src/dfa
  ${antlr4_SOURCE_DIR}/runtime/Cpp/runtime/src/misc
  ${antlr4_SOURCE_DIR}/runtime/Cpp/runtime/src/tree
)

set( MPARK_VARIANT_INCLUDE_DIRS
  ${PROJECT_SOURCE_DIR}/external/mpark-variant/master
)

set( TINY_OPTIONAL_INCLUDE_DIRS
  ${PROJECT_SOURCE_DIR}/external/tiny
)

add_executable(azslc ${azslc_LocalSRC} ${azslc_PlatformSRC} ${azslc_GeneratedSrc} ${azslc_ExternalSRC})

target_compile_features(azslc PRIVATE cxx_std_17)

target_include_directories(
  azslc
  PRIVATE
  ${PROJECT_SOURCE_DIR}
  ${PROJECT_SOURCE_DIR}/external
  ${PROJECT_SOURCE_DIR}/../Platform/Common/src
  ${ANTLR4CPP_INCLUDE_DIRS}
  ${MPARK_VARIANT_INCLUDE_DIRS}
  ${TINY_OPTIONAL_INCLUDE_DIRS}
)

source_group("azslc" FILES ${azslc_LocalSRC})
source_group("generated" FILES ${azslc_GeneratedSrc})
source_group("external" FILES ${azslc_ExternalSRC})
source_group("platforms" FILES ${azslc_PlatformSRC})

if (MSVC)
  set_property(TARGET azslc PROPERTY MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>")
  target_compile_options(azslc
    PRIVATE
    "/MP" # Build with multiple processes
    "/permissive-"
    "/Zc:__cplusplus"
    "/utf-8")
  target_compile_definitions(azslc
    PRIVATE
    _CRT_SECURE_NO_WARNINGS
    _SILENCE_CXX17_CODECVT_HEADER_DEPRECATION_WARNING
    ANTLR4CPP_STATIC)
elseif (UNIX AND NOT APPLE)
  target_link_libraries(azslc PRIVATE stdc++fs)
endif()

target_link_libraries(azslc PRIVATE CLI11::CLI11 antlr4_static)

if (MSVC)
  add_custom_command(TARGET azslc POST_BUILD
      COMMAND ${CMAKE_COMMAND} -E make_directory "${PROJECT_SOURCE_DIR}/../bin/${buildPlatform}/$(Configuration)"
      COMMAND ${CMAKE_COMMAND} -E copy "${CMAKE_BINARY_DIR}/$(Configuration)/azslc.exe" "${PROJECT_SOURCE_DIR}/../bin/${buildPlatform}/$(Configuration)"
)
endif()

set_property(GLOBAL PROPERTY USE_FOLDERS ON)

set_target_properties( azslc
    PROPERTIES
    EXECUTABLE_OUTPUT_PATH "${CMAKE_BINARY_DIR}/"
    LIBRARY_OUTPUT_PATH "${CMAKE_BINARY_DIR}/"
)

set_property(DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR} PROPERTY VS_STARTUP_PROJECT azslc)
