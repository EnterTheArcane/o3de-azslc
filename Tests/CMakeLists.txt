#
# Copyright (c) Contributors to the Open 3D Engine Project.
# For complete copyright and license terms please see the LICENSE at the root of this distribution.
#
# SPDX-License-Identifier: Apache-2.0 OR MIT
#
#

set(BUILDNAME "azslc")
include(CTest)

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
file(GLOB _syntax_pass "${CMAKE_CURRENT_SOURCE_DIR}/Syntax/*.azsl")
file(GLOB _syntax_fail "${CMAKE_CURRENT_SOURCE_DIR}/Syntax/AsErrors/*.azsl")
azslc_add_simple_tests(Syntax "--syntax" FALSE ${_syntax_pass})
azslc_add_simple_tests(Syntax "--syntax" TRUE ${_syntax_fail})

# Semantic
file(GLOB _semantic_pass "${CMAKE_CURRENT_SOURCE_DIR}/Semantic/*.azsl")
file(GLOB _semantic_fail "${CMAKE_CURRENT_SOURCE_DIR}/Semantic/AsError/*.azsl")
azslc_add_simple_tests(Semantic "--semantic" FALSE ${_semantic_pass})
azslc_add_simple_tests(Semantic "--semantic" TRUE ${_semantic_fail})

# Samples
file(GLOB _samples_pass "${CMAKE_CURRENT_SOURCE_DIR}/Samples/*.azsl")
file(GLOB _samples_fail "${CMAKE_CURRENT_SOURCE_DIR}/Samples/AsError/*.azsl")
azslc_add_simple_tests(Samples "" FALSE ${_samples_pass})
azslc_add_simple_tests(Samples "" TRUE ${_samples_fail})

# Emission
file(GLOB _emission_pass "${CMAKE_CURRENT_SOURCE_DIR}/Emission/*.azsl")
foreach(test_file IN LISTS _emission_pass)
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

file(GLOB _emission_fail "${CMAKE_CURRENT_SOURCE_DIR}/Emission/AsError/*.azsl")
foreach(test_file IN LISTS _emission_fail)
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

file(GLOB_RECURSE _advanced_tests "${CMAKE_CURRENT_SOURCE_DIR}/Advanced/*.py")
foreach(test_file IN LISTS _advanced_tests)
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
