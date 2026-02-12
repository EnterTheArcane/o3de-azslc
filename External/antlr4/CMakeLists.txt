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
    set(_one_value TARGET LEXER_GRAMMAR PARSER_GRAMMAR OUTPUT_DIR)
    set(_multi_value "")
    cmake_parse_arguments(_arg "${_options}" "${_one_value}" "${_multi_value}" ${ARGN})

    if(NOT _arg_TARGET)
        message(FATAL_ERROR "TARGET is required")
    endif()

    if(NOT _arg_OUTPUT_DIR)
        message(FATAL_ERROR "OUTPUT_DIR is required")
    endif()

    if(_arg_LEXER_GRAMMAR AND NOT IS_ABSOLUTE "${_arg_LEXER_GRAMMAR}")
        cmake_path(ABSOLUTE_PATH _arg_LEXER_GRAMMAR BASE_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}")
    endif()
    if(_arg_PARSER_GRAMMAR AND NOT IS_ABSOLUTE "${_arg_PARSER_GRAMMAR}")
        cmake_path(ABSOLUTE_PATH _arg_PARSER_GRAMMAR BASE_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}")
    endif()

    file(MAKE_DIRECTORY "${_arg_OUTPUT_DIR}")

    set(_antlr_flags -Dlanguage=Cpp -o "${_arg_OUTPUT_DIR}" -listener -visitor)
    set(_all_generated "")
    set(_lexer_outputs "")

    if(_arg_LEXER_GRAMMAR)
        get_filename_component(_lexer_name "${_arg_LEXER_GRAMMAR}" NAME_WE)

        set(_lexer_outputs
            "${_arg_OUTPUT_DIR}/${_lexer_name}.cpp"
            "${_arg_OUTPUT_DIR}/${_lexer_name}.h"
            "${_arg_OUTPUT_DIR}/${_lexer_name}.tokens"
            "${_arg_OUTPUT_DIR}/${_lexer_name}.interp"
        )

        add_custom_command(
            OUTPUT ${_lexer_outputs}
            COMMAND "${Java_JAVA_EXECUTABLE}" -jar "${ANTLR4_JAR_PATH}" ${_antlr_flags} "${_arg_LEXER_GRAMMAR}"
            DEPENDS "${_arg_LEXER_GRAMMAR}"
            WORKING_DIRECTORY "${_arg_OUTPUT_DIR}"
            COMMENT "Generating lexer from ${_lexer_name}.g4"
            VERBATIM
            CODEGEN
        )

        # Only the C++ files are compiled; .tokens/.interp are build artifacts
        list(APPEND _all_generated
            "${_arg_OUTPUT_DIR}/${_lexer_name}.cpp"
            "${_arg_OUTPUT_DIR}/${_lexer_name}.h"
        )
    endif()

    if(_arg_PARSER_GRAMMAR)
        get_filename_component(_parser_name "${_arg_PARSER_GRAMMAR}" NAME_WE)

        set(_parser_outputs
            "${_arg_OUTPUT_DIR}/${_parser_name}.cpp"
            "${_arg_OUTPUT_DIR}/${_parser_name}.h"
            "${_arg_OUTPUT_DIR}/${_parser_name}.tokens"
            "${_arg_OUTPUT_DIR}/${_parser_name}.interp"
            # Listener
            "${_arg_OUTPUT_DIR}/${_parser_name}Listener.cpp"
            "${_arg_OUTPUT_DIR}/${_parser_name}Listener.h"
            "${_arg_OUTPUT_DIR}/${_parser_name}BaseListener.cpp"
            "${_arg_OUTPUT_DIR}/${_parser_name}BaseListener.h"
            # Visitor
            "${_arg_OUTPUT_DIR}/${_parser_name}Visitor.cpp"
            "${_arg_OUTPUT_DIR}/${_parser_name}Visitor.h"
            "${_arg_OUTPUT_DIR}/${_parser_name}BaseVisitor.cpp"
            "${_arg_OUTPUT_DIR}/${_parser_name}BaseVisitor.h"
        )

        add_custom_command(
            OUTPUT ${_parser_outputs}
            COMMAND "${Java_JAVA_EXECUTABLE}" -jar "${ANTLR4_JAR_PATH}" ${_antlr_flags} "${_arg_PARSER_GRAMMAR}"
            DEPENDS ${_arg_PARSER_GRAMMAR} ${_lexer_outputs}
            WORKING_DIRECTORY "${_arg_OUTPUT_DIR}"
            COMMENT "Generating parser from ${_parser_name}.g4"
            VERBATIM
            CODEGEN
        )

        list(APPEND _all_generated
            "${_arg_OUTPUT_DIR}/${_parser_name}.cpp"
            "${_arg_OUTPUT_DIR}/${_parser_name}.h"
            # Listener
            "${_arg_OUTPUT_DIR}/${_parser_name}Listener.cpp"
            "${_arg_OUTPUT_DIR}/${_parser_name}Listener.h"
            "${_arg_OUTPUT_DIR}/${_parser_name}BaseListener.cpp"
            "${_arg_OUTPUT_DIR}/${_parser_name}BaseListener.h"
            # Visitor
            "${_arg_OUTPUT_DIR}/${_parser_name}Visitor.cpp"
            "${_arg_OUTPUT_DIR}/${_parser_name}Visitor.h"
            "${_arg_OUTPUT_DIR}/${_parser_name}BaseVisitor.cpp"
            "${_arg_OUTPUT_DIR}/${_parser_name}BaseVisitor.h"
        )
    endif()

    set_source_files_properties(${_all_generated} PROPERTIES GENERATED TRUE)
    target_sources(${_arg_TARGET} PRIVATE ${_all_generated})
    target_include_directories(${_arg_TARGET} PRIVATE "${_arg_OUTPUT_DIR}")
endfunction()
