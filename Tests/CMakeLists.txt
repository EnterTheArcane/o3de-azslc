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
            TIMEOUT 30
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
        TIMEOUT 30
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
        TIMEOUT 30
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
        TIMEOUT 120
    )
endforeach()

# Platform Specific
set(platforms_dir "${CMAKE_CURRENT_SOURCE_DIR}/../Platform")
if(IS_DIRECTORY "${platforms_dir}")
    file(GLOB platform_dirs "${platforms_dir}/*")
    foreach(platform_dir IN LISTS platform_dirs)
        if(NOT IS_DIRECTORY "${platform_dir}")
            continue()
        endif()
        set(platform_tests "${platform_dir}/tests")
        if(NOT IS_DIRECTORY "${platform_tests}")
            continue()
        endif()

        get_filename_component(plat_name "${platform_dir}" NAME)
        file(GLOB_RECURSE _plat_azsl "${platform_tests}/*.azsl")
        foreach(test_file IN LISTS _plat_azsl)
            file(RELATIVE_PATH rel_path "${CMAKE_CURRENT_SOURCE_DIR}/.." "${test_file}")
            string(REGEX REPLACE "\\.[^.]*$" "" test_name "${rel_path}")

            # Determine compiler flag from path conventions.
            string(TOLOWER "${test_file}" _lower_path)
            set(platform_compiler_flag "")
            if(_lower_path MATCHES "/syntax/")
                set(platform_compiler_flag "--syntax")
            elseif(_lower_path MATCHES "/semantic/")
                set(platform_compiler_flag "--semantic")
            endif()

            set(platform_expect_fail FALSE)
            if(_lower_path MATCHES "/aserror")
                set(platform_expect_fail TRUE)
            endif()

            set(cmd_args
                "${AZSLC_CTEST_RUNNER}"
                --compiler "$<TARGET_FILE:azslc>"
                --file "${test_file}"
                --mode simple
            )
            if(NOT "${platform_compiler_flag}" STREQUAL "")
                list(APPEND cmd_args "--compiler-flag=${platform_compiler_flag}")
            endif()
            if(platform_expect_fail)
                list(APPEND cmd_args --expect-fail)
            endif()

            get_filename_component(stem "${test_file}" NAME_WE)
            if(stem MATCHES "^wip-")
                list(APPEND cmd_args --wip)
            endif()

            add_test(
                NAME "${test_name}"
                COMMAND ${Python3_EXECUTABLE} ${cmd_args}
                WORKING_DIRECTORY "${platform_tests}"
            )

            set(labels "Platform;${plat_name}")
            if(stem MATCHES "^wip-")
                list(APPEND labels "WIP")
            endif()
            set_tests_properties("${test_name}" PROPERTIES
                LABELS "${labels}"
                SKIP_RETURN_CODE 77
                TIMEOUT 30
            )
        endforeach()
    endforeach()
endif()
