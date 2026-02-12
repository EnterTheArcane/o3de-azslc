include(FetchContent)

set(ANTLR_BUILD_CPP_TESTS OFF)
set(ANTLR_BUILD_SHARED OFF)
set(ANTLR_BUILD_STATIC ON)
set(TRACE_ATN OFF)

FetchContent_Declare(antlr4
    URL https://github.com/antlr/antlr4/archive/8e6fd9147b3c9d36b60e2b6656871a55227efb1b.tar.gz
    URL_HASH SHA256=c52fb2a90ae082ab5b5ec6b41f6c0e66f691962e4b4f772580e5da678fe51fed
    SOURCE_SUBDIR runtime/Cpp
)

FetchContent_MakeAvailable(antlr4)

add_library(External::antlr4 ALIAS antlr4_static)

find_package(Java COMPONENTS Runtime REQUIRED)
set(ANTLR4_JAR_PATH "${CMAKE_CURRENT_SOURCE_DIR}/antlr4-4.13.2-complete.jar" CACHE FILEPATH "")

function(antlr4_generate)
    set(_options "")
    set(_one_value TARGET LEXER_GRAMMAR PARSER_GRAMMAR OUTPUT_DIR LISTENER VISITOR)
    set(_multi_value "")
    cmake_parse_arguments(ARG "${_options}" "${_one_value}" "${_multi_value}" ${ARGN})

    if(NOT ARG_TARGET)
        message(FATAL_ERROR "antlr4_generate: TARGET is required")
    endif()
    if(NOT ARG_OUTPUT_DIR)
        message(FATAL_ERROR "antlr4_generate: OUTPUT_DIR is required")
    endif()

    file(MAKE_DIRECTORY "${ARG_OUTPUT_DIR}")

    set(_antlr_flags -Dlanguage=Cpp -o "${ARG_OUTPUT_DIR}")

    if(ARG_LISTENER)
        list(APPEND _antlr_flags -listener)
    else()
        list(APPEND _antlr_flags -no-listener)
    endif()

    if(ARG_VISITOR)
        list(APPEND _antlr_flags -visitor)
    else()
        list(APPEND _antlr_flags -no-visitor)
    endif()

    set(_all_generated "")
    set(_lexer_outputs "")

    if(ARG_LEXER_GRAMMAR)
        get_filename_component(_lexer_name "${ARG_LEXER_GRAMMAR}" NAME_WE)

        # ANTLR also produces .tokens and .interp alongside the C++ sources.
        # The .tokens file is required by the parser step (tokenVocab), so it
        # must be listed as an OUTPUT to establish a proper dependency chain.
        set(_lexer_outputs
            "${ARG_OUTPUT_DIR}/${_lexer_name}.cpp"
            "${ARG_OUTPUT_DIR}/${_lexer_name}.h"
            "${ARG_OUTPUT_DIR}/${_lexer_name}.tokens"
            "${ARG_OUTPUT_DIR}/${_lexer_name}.interp"
        )

        add_custom_command(
            OUTPUT  ${_lexer_outputs}
            COMMAND "${Java_JAVA_EXECUTABLE}" -jar "${ANTLR4_JAR_PATH}"
                    ${_antlr_flags}
                    "${ARG_LEXER_GRAMMAR}"
            DEPENDS "${ARG_LEXER_GRAMMAR}"
            WORKING_DIRECTORY "${ARG_OUTPUT_DIR}"
            COMMENT "ANTLR4: Generating lexer from ${_lexer_name}.g4"
            VERBATIM
        )

        # Only the C++ files are compiled; .tokens/.interp are build artifacts
        list(APPEND _all_generated
            "${ARG_OUTPUT_DIR}/${_lexer_name}.cpp"
            "${ARG_OUTPUT_DIR}/${_lexer_name}.h"
        )
    endif()

    if(ARG_PARSER_GRAMMAR)
        get_filename_component(_parser_name "${ARG_PARSER_GRAMMAR}" NAME_WE)

        set(_parser_outputs
            "${ARG_OUTPUT_DIR}/${_parser_name}.cpp"
            "${ARG_OUTPUT_DIR}/${_parser_name}.h"
            "${ARG_OUTPUT_DIR}/${_parser_name}.tokens"
            "${ARG_OUTPUT_DIR}/${_parser_name}.interp"
        )

        # Listener outputs
        if(ARG_LISTENER)
            list(APPEND _parser_outputs
                "${ARG_OUTPUT_DIR}/${_parser_name}Listener.cpp"
                "${ARG_OUTPUT_DIR}/${_parser_name}Listener.h"
                "${ARG_OUTPUT_DIR}/${_parser_name}BaseListener.cpp"
                "${ARG_OUTPUT_DIR}/${_parser_name}BaseListener.h"
            )
        endif()

        # Visitor outputs
        if(ARG_VISITOR)
            list(APPEND _parser_outputs
                "${ARG_OUTPUT_DIR}/${_parser_name}Visitor.cpp"
                "${ARG_OUTPUT_DIR}/${_parser_name}Visitor.h"
                "${ARG_OUTPUT_DIR}/${_parser_name}BaseVisitor.cpp"
                "${ARG_OUTPUT_DIR}/${_parser_name}BaseVisitor.h"
            )
        endif()

        # The parser depends on its own .g4, but critically also on the lexer
        # outputs — ANTLR needs the lexer's .tokens file (tokenVocab) to
        # generate the parser.  By depending on the lexer OUTPUT list, CMake
        # guarantees the lexer step runs first.
        set(_parser_depends "${ARG_PARSER_GRAMMAR}")
        if(_lexer_outputs)
            list(APPEND _parser_depends ${_lexer_outputs})
        endif()

        add_custom_command(
            OUTPUT  ${_parser_outputs}
            COMMAND "${Java_JAVA_EXECUTABLE}" -jar "${ANTLR4_JAR_PATH}"
                    ${_antlr_flags}
                    "${ARG_PARSER_GRAMMAR}"
            DEPENDS ${_parser_depends}
            WORKING_DIRECTORY "${ARG_OUTPUT_DIR}"
            COMMENT "ANTLR4: Generating parser from ${_parser_name}.g4"
            VERBATIM
        )

        # Only the C++ files are compiled
        list(APPEND _all_generated
            "${ARG_OUTPUT_DIR}/${_parser_name}.cpp"
            "${ARG_OUTPUT_DIR}/${_parser_name}.h"
        )
        if(ARG_LISTENER)
            list(APPEND _all_generated
                "${ARG_OUTPUT_DIR}/${_parser_name}Listener.cpp"
                "${ARG_OUTPUT_DIR}/${_parser_name}Listener.h"
                "${ARG_OUTPUT_DIR}/${_parser_name}BaseListener.cpp"
                "${ARG_OUTPUT_DIR}/${_parser_name}BaseListener.h"
            )
        endif()
        if(ARG_VISITOR)
            list(APPEND _all_generated
                "${ARG_OUTPUT_DIR}/${_parser_name}Visitor.cpp"
                "${ARG_OUTPUT_DIR}/${_parser_name}Visitor.h"
                "${ARG_OUTPUT_DIR}/${_parser_name}BaseVisitor.cpp"
                "${ARG_OUTPUT_DIR}/${_parser_name}BaseVisitor.h"
            )
        endif()
    endif()

    # Export the list of generated C++ files to the caller
    set(ANTLR4_GENERATED_SOURCES ${_all_generated} PARENT_SCOPE)
endfunction()
