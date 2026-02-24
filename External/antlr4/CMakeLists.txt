#
# Copyright (c) Contributors to the Open 3D Engine Project.
# For complete copyright and license terms please see the LICENSE at the root of this distribution.
#
# SPDX-License-Identifier: Apache-2.0 OR MIT
#
#

set(ANTLR_BUILD_CPP_TESTS OFF)
set(ANTLR_BUILD_SHARED OFF)
set(ANTLR_BUILD_STATIC ON)
set(TRACE_ATN OFF)

FetchContent_Declare(antlr4
    URL https://github.com/antlr/antlr4/archive/8e6fd9147b3c9d36b60e2b6656871a55227efb1b.tar.gz # 2026-01-01
    URL_HASH SHA256=c52fb2a90ae082ab5b5ec6b41f6c0e66f691962e4b4f772580e5da678fe51fed
    SOURCE_SUBDIR runtime/Cpp
    DOWNLOAD_NO_PROGRESS ON
)

FetchContent_MakeAvailable(antlr4)

add_library(External::antlr4 ALIAS antlr4_static)

find_package(Java COMPONENTS Runtime REQUIRED)
set(ANTLR4_JAR_PATH "${CMAKE_CURRENT_SOURCE_DIR}/antlr4-4.13.2-complete.jar" CACHE FILEPATH "")
