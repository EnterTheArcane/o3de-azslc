cmake_minimum_required(VERSION 3.25)
cmake_policy(SET CMP0077 NEW)
cmake_policy(SET CMP0135 NEW)

project(azslc)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>")
set(CMAKE_POLICY_VERSION_MINIMUM "3.5")

set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY $<1:${CMAKE_BINARY_DIR}/lib>)
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY $<1:${CMAKE_BINARY_DIR}/lib>)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY $<1:${CMAKE_BINARY_DIR}/bin>)

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

add_subdirectory(External/antlr4)
add_subdirectory(External/cli11)
add_subdirectory(External/jsoncpp)

set(ANTLR4_GENERATED_DIR "${CMAKE_BINARY_DIR}/generated")

antlr4_generate(
    TARGET azslc
    LEXER_GRAMMAR "${CMAKE_CURRENT_SOURCE_DIR}/Source/azslLexer.g4"
    PARSER_GRAMMAR "${CMAKE_CURRENT_SOURCE_DIR}/Source/azslParser.g4"
    OUTPUT_DIR "${ANTLR4_GENERATED_DIR}"
    LISTENER TRUE
    VISITOR FALSE
)

file(GLOB_RECURSE SOURCES
    Platform/*.cpp
    Platform/*.h
    Source/*.cpp
    Source/*.h
    Source/*.tpl
)

add_executable(azslc ${SOURCES} ${ANTLR4_GENERATED_SOURCES})

set_source_files_properties(${ANTLR4_GENERATED_SOURCES} PROPERTIES GENERATED TRUE)

target_include_directories(azslc PRIVATE
    Platform/Common/Source
    Source
)

target_link_libraries(azslc PRIVATE
    External::antlr4
    External::cli11
    External::jsoncpp
)

set_property(
    DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}
    PROPERTY VS_STARTUP_PROJECT azslc
)
