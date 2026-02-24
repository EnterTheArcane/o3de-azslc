#
# Copyright (c) Contributors to the Open 3D Engine Project.
# For complete copyright and license terms please see the LICENSE at the root of this distribution.
#
# SPDX-License-Identifier: Apache-2.0 OR MIT
#
#

cmake_minimum_required(VERSION 4.1)

set(CMAKE_EXPERIMENTAL_EXPORT_PACKAGE_INFO "b80be207-778e-46ba-8080-b23bba22639e")

project(azslc VERSION 1.9.0)

option(AZSLC_VERSION_ONLY "Exit after project() for version extraction" OFF)

set(CMAKE_CXX_SCAN_FOR_MODULES OFF)
set(CMAKE_CXX_STANDARD 20)
set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>")
set(CMAKE_POLICY_VERSION_MINIMUM "3.5")

if(AZSLC_VERSION_ONLY)
    return()
endif()

include(CTest)

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

add_subdirectory(Tests)

include(GNUInstallDirs)
include(CMakePackageConfigHelpers)

install(
    TARGETS azslc
    EXPORT azslc-targets
    RUNTIME DESTINATION ${CMAKE_INSTALL_BINDIR}
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
    ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
)

install(
    EXPORT azslc-targets
    NAMESPACE azslc::
    DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/azslc
    FILE azslc-targets.cmake
)

configure_package_config_file(
    config.cmake.in
    ${CMAKE_CURRENT_BINARY_DIR}/azslc-config.cmake
    INSTALL_DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/azslc
)

write_basic_package_version_file(
    ${CMAKE_CURRENT_BINARY_DIR}/azslc-config-version.cmake
    VERSION ${PROJECT_VERSION}
    COMPATIBILITY SameMajorVersion
)

install(
    FILES
    ${CMAKE_CURRENT_BINARY_DIR}/azslc-config.cmake
    ${CMAKE_CURRENT_BINARY_DIR}/azslc-config-version.cmake
    DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/azslc
)

install(PACKAGE_INFO ${PROJECT_NAME}
    EXPORT azslc-targets
    VERSION ${PROJECT_VERSION}
    COMPAT_VERSION ${PROJECT_VERSION_MAJOR}.0.0
    VERSION_SCHEMA simple
    LOWER_CASE_FILE
)
