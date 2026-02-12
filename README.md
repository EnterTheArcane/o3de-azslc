# Amazon Shading Language Compiler

AZSLC is a stand-alone command line compiler for the Amazon Shading Language.
It converts Amazon Shading Language (AZSL) shaders to High Level Shading Language Shader Model 6+ (HLSL) shaders.

For more information, see (https://o3de.org/docs/atom-guide/dev-guide/shaders/)

# Features and Goals

AZSLC is a compiler for the Amazon Shading Language, which is a thin extension of HLSL. 
It unifies resource bindings across all supported graphics hardware using binding strategy which models modern API such as DirectX 12 and Vulkan. 
Future plans include support for interfaces, generics and associated types and aims to bring the shader languages 
closer to modern programming languages while still allowing users to write shaders in plain HLSL code.

Currently supported graphics APIs are:
 - DirectX 12 (Windows)
 - Vulkan (Windows, Linux, Android)
 - Metal 2 (macOS, iOS)

# Build Instructions

## Supported build architectures:
 - Windows 10 (win_x64)
 - MacOSX 10.14.0 or newer
 - linux (tested with debian)

## Prerequisites

### All platforms
Make sure Python 3.7+ is in your `$PATH` (as 'python'), and run:
```
python scripts/test.and.py
or
python scripts/test.and.py --dev D:\o3de
```
(`D:\o3de` should be replaced with your local path for the O3DE root directory)

The script will tell you the prerequisites per platform, which are also detailed below.
If all prerequisites are installed, the script will make, build and test the shader compiler.


### Windows
 - Python 3.7
 - MSBuild 15.9 or higher (from https://github.com/Microsoft/msbuild, MS Build Tools 2019 or VS2019)
 - CMake 3.25+
 - [PyYAML](https://pyyaml.org/) (`pip install pyyaml`) - only required to run the tests
 - (optional) Visual Studio 2019 for IDE
 - (optional) Java JDK 1.6 or higher (only required to regenerate the Antlr grammar)

### Mac
 - Python 3.7
 - CMake 3.25+
 - Apple LLVM 9.0.0 (clang-900.0.39.2) or newer (tested on 9.0.0 and 10.0.0)
   - comes with Xcode 9.2 or Command Line Tools for Xcode 9.2 or newer
 - [PyYAML](https://pyyaml.org/) (`pip install pyyaml`) - only required to run the tests
 - (optional) Java JDK 1.6 or higher (only required to regenerate the Antlr grammar)

### Linux
 - Python 3.7
 - gcc 8
 - CMake 3.25+
 - sudo apt-get install uuid-dev (or equivalent in your distribution)
 - [PyYAML](https://pyyaml.org/) (`pip install pyyaml`) - only required to run the tests
 - (optional) Java JDK 1.6 or higher (only required to regenerate the Antlr grammar)

## (optional) Regenerate the AZSL ANTLR grammar
1. Install Java JDK (version 1.6 or higher)
2. (Windows) The generated source files can be recreated by running `regenerate_azsl_antlr.bat` script under `src` folder, it will regenerate the files under `src/generated`.
 - Should work on MacOSX as well, but hasn't been tested. Follow the steps in the batch file to invoke Java and rebuild the generated sources

## (optional) azslLexer.g4 keyword changes
You'll probably need to regenerate the `AzslcPredefinedTypes.h` header file if you made a change to the lexer keywords. For that purpose, execute regenerate-predefined-types.bat (or simply directly `python exportKeywords.py` if not on windows)

# Testing

The `Tests` folder includes all the unit and integration tests for AZSLC.

Tests require Python 3.7 to run. And pyyaml for a complete run. (do pip install pyyaml)

For Windows, run either `launch_tests.bat` or `launch_tests_debug.bat` to launch the test suite.

For MacOSX, run either `launch_tests.sh` or `launch_tests_debug.sh` to launch the test suite.

## License

For terms please see the LICENSE*.TXT file at the root of this distribution.
