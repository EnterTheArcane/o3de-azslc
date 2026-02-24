#
# Copyright (c) Contributors to the Open 3D Engine Project.
# For complete copyright and license terms please see the LICENSE at the root of this distribution.
#
# SPDX-License-Identifier: Apache-2.0 OR MIT
#
#

set(BUILDNAME "azslc")
set_property(GLOBAL PROPERTY CTEST_TARGETS_ADDED 1)

include(CTest)

if(NOT BUILD_TESTING)
    return()
endif()

find_package(Python3 REQUIRED COMPONENTS Interpreter)

set(AZSLC_CTEST_RUNNER "${CMAKE_CURRENT_SOURCE_DIR}/ctest-runner.py")

function(azslc_add_simple_tests label compiler_flag expect_fail)
    foreach(test_file IN LISTS ARGN)
        file(RELATIVE_PATH rel_path "${CMAKE_CURRENT_SOURCE_DIR}" "${test_file}")
        # Strip extension to form the test name  (e.g. "Syntax/empty")
        string(REGEX REPLACE "\\.[^.]*$" "" test_name "${rel_path}")

        set(cmd_args
            "${AZSLC_CTEST_RUNNER}"
            --compiler "$<TARGET_FILE:azslc>"
            --file "${test_file}"
            --mode simple
        )
        if(NOT "${compiler_flag}" STREQUAL "")
            list(APPEND cmd_args "--compiler-flag=${compiler_flag}")
        endif()
        if(expect_fail)
            list(APPEND cmd_args --expect-fail)
        endif()

        get_filename_component(stem "${test_file}" NAME_WE)
        if(stem MATCHES "^wip-")
            list(APPEND cmd_args --wip)
        endif()

        add_test(
            NAME "${test_name}"
            COMMAND ${Python3_EXECUTABLE} ${cmd_args}
            WORKING_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}"
        )

        set(labels "${label}")
        if(stem MATCHES "^wip-")
            list(APPEND labels "WIP")
        endif()

        set_tests_properties("${test_name}" PROPERTIES
            LABELS "${labels}"
            SKIP_RETURN_CODE 77
            TIMEOUT 60
        )
    endforeach()
endfunction()

# Syntax
block()
    file(GLOB pass_files "${CMAKE_CURRENT_SOURCE_DIR}/Syntax/*.azsl")
    file(GLOB fail_files "${CMAKE_CURRENT_SOURCE_DIR}/Syntax/AsErrors/*.azsl")
    azslc_add_simple_tests(Syntax "--syntax" FALSE ${pass_files})
    azslc_add_simple_tests(Syntax "--syntax" TRUE ${fail_files})
endblock()

# Semantic
block()
    file(GLOB pass_files "${CMAKE_CURRENT_SOURCE_DIR}/Semantic/*.azsl")
    file(GLOB fail_files "${CMAKE_CURRENT_SOURCE_DIR}/Semantic/AsError/*.azsl")
    azslc_add_simple_tests(Semantic "--semantic" FALSE ${pass_files})
    azslc_add_simple_tests(Semantic "--semantic" TRUE ${fail_files})
endblock()

# Samples
block()
    file(GLOB pass_files "${CMAKE_CURRENT_SOURCE_DIR}/Samples/*.azsl")
    file(GLOB fail_files "${CMAKE_CURRENT_SOURCE_DIR}/Samples/AsError/*.azsl")
    azslc_add_simple_tests(Samples "" FALSE ${pass_files})
    azslc_add_simple_tests(Samples "" TRUE ${fail_files})
endblock()

# Emission
block()
    file(GLOB test_files "${CMAKE_CURRENT_SOURCE_DIR}/Emission/*.azsl")
    foreach(test_file IN LISTS test_files)
        file(RELATIVE_PATH rel_path "${CMAKE_CURRENT_SOURCE_DIR}" "${test_file}")
        string(REGEX REPLACE "\\.[^.]*$" "" test_name "${rel_path}")

        add_test(
            NAME "${test_name}"
            COMMAND ${Python3_EXECUTABLE} "${AZSLC_CTEST_RUNNER}"
            --compiler "$<TARGET_FILE:azslc>"
            --file "${test_file}"
            --mode emission
            WORKING_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}"
        )

        set_tests_properties("${test_name}" PROPERTIES
            LABELS "Emission"
            SKIP_RETURN_CODE 77
            TIMEOUT 60
        )
    endforeach()
endblock()

block()
    file(GLOB test_files "${CMAKE_CURRENT_SOURCE_DIR}/Emission/AsError/*.azsl")
    foreach(test_file IN LISTS test_files)
        file(RELATIVE_PATH rel_path "${CMAKE_CURRENT_SOURCE_DIR}" "${test_file}")
        string(REGEX REPLACE "\\.[^.]*$" "" test_name "${rel_path}")

        add_test(
            NAME "${test_name}"
            COMMAND ${Python3_EXECUTABLE} "${AZSLC_CTEST_RUNNER}"
            --compiler "$<TARGET_FILE:azslc>"
            --file "${test_file}"
            --mode emission-error
            WORKING_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}"
        )

        set_tests_properties("${test_name}" PROPERTIES
            LABELS "Emission"
            SKIP_RETURN_CODE 77
            TIMEOUT 60
        )
    endforeach()
endblock()

block()
    file(GLOB_RECURSE test_files "${CMAKE_CURRENT_SOURCE_DIR}/Advanced/*.py")
    foreach(test_file IN LISTS test_files)
        if(test_file MATCHES "__pycache__")
            continue()
        endif()

        get_filename_component(stem "${test_file}" NAME_WE)
        if(stem STREQUAL "__init__")
            continue()
        endif()

        file(RELATIVE_PATH rel_path "${CMAKE_CURRENT_SOURCE_DIR}" "${test_file}")
        string(REGEX REPLACE "\\.[^.]*$" "" test_name "${rel_path}")

        add_test(
            NAME "${test_name}"
            COMMAND ${Python3_EXECUTABLE} "${AZSLC_CTEST_RUNNER}"
            --compiler "$<TARGET_FILE:azslc>"
            --file "${test_file}"
            --mode advanced
            WORKING_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}"
        )

        set_tests_properties("${test_name}" PROPERTIES
            LABELS "Advanced"
            SKIP_RETURN_CODE 77
            TIMEOUT 240
        )
    endforeach()
endblock()
