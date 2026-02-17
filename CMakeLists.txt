#
# Copyright (c) Contributors to the Open 3D Engine Project.
# For complete copyright and license terms please see the LICENSE at the root of this distribution.
#
# SPDX-License-Identifier: Apache-2.0 OR MIT
#
#

cmake_minimum_required(VERSION 3.31)

cmake_policy(SET CMP0077 NEW)
cmake_policy(SET CMP0135 NEW)

project(azslc VERSION 1.9.0)

option(AZSLC_VERSION_ONLY "Exit after project() for version extraction" OFF)
option(ENABLE_TESTS "Enable tests" ON)

set(CMAKE_CXX_SCAN_FOR_MODULES OFF)
set(CMAKE_CXX_STANDARD 20)
set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>")
set(CMAKE_POLICY_VERSION_MINIMUM "3.5")

if(AZSLC_VERSION_ONLY)
    return()
endif()

include(CMake/Utility.cmake)
include(CMake/CodeSigning.cmake)

if(MSVC)
    add_compile_options(
        /MP
        /permissive-
        /Zc:__cplusplus
        /utf-8
    )
    add_compile_definitions(
        _CRT_SECURE_NO_WARNINGS
    )
else()
    add_compile_options(
        -Wno-language-extension-token
    )
endif()

add_executable(azslc)

file(GLOB_RECURSE SOURCES
    Platform/*.cpp
    Platform/*.h
    Source/*.cpp
    Source/*.h
    Source/*.tpl
)

target_sources(azslc PRIVATE ${SOURCES})

if(WIN32)
    configure_file(Platform/Windows/Source/azslc.rc.in "${CMAKE_BINARY_DIR}/_generated/azslc.rc")
    target_sources(azslc PRIVATE "${CMAKE_BINARY_DIR}/_generated/azslc.rc")
endif()

target_compile_definitions(azslc PRIVATE
    $<$<CONFIG:Debug>:AZSLC_DEBUG>
    AZSLC_MAJOR=${PROJECT_VERSION_MAJOR}
    AZSLC_MINOR=${PROJECT_VERSION_MINOR}
    AZSLC_PATCH=${PROJECT_VERSION_PATCH}
)

target_include_directories(azslc PRIVATE
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/Source>
)

add_subdirectory(External)
target_link_libraries(azslc PRIVATE
    External::antlr4
    External::cli11
    External::jsoncpp
)

set_target_properties(azslc PROPERTIES
    RUNTIME_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/bin"
)

azslc_option(AZSLC_OUTPUT_NAME "" STRING "Override the output executable name")

if(AZSLC_OUTPUT_NAME)
    set_target_properties(azslc PROPERTIES OUTPUT_NAME "${AZSLC_OUTPUT_NAME}")
endif()

antlr4_generate(
    TARGET azslc
    LEXER_GRAMMAR Source/Grammar/azslLexer.g4
    PARSER_GRAMMAR Source/Grammar/azslParser.g4
    OUTPUT_DIR "${CMAKE_BINARY_DIR}/_generated"
)

set_property(
    DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}
    PROPERTY VS_STARTUP_PROJECT azslc
)

azslc_sign_target(azslc)

if(ENABLE_TESTS)
    enable_testing()
    add_subdirectory(Tests)
endif()
