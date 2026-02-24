# Amazon Shading Language Compiler

AZSLC is a stand-alone command-line compiler for the Amazon Shading Language (AZSL).
It transpiles AZSL shaders into High Level Shading Language Shader Model 6+ (HLSL) shaders.

For more information, see <https://o3de.org/docs/atom-guide/dev-guide/shaders>

## Features and Goals

AZSL is a thin extension of HLSL that unifies resource bindings across all supported graphics APIs using a binding strategy modeled after modern APIs such as DirectX 12 and Vulkan. AZSLC aims to bring shader languages closer to modern programming languages -- with support for interfaces, generics, associated types, and more -- while still allowing users to write shaders in plain HLSL.

Currently supported graphics APIs:

- DirectX 12 (Windows)
- Metal 2 (macOS, iOS)
- Vulkan (Windows, Linux, Android)

## Prerequisites

### Required

| Tool | Version |
|---|---|
| **CMake** | 3.31+ |
| **C++20 compiler** | See [platform notes](#platform-notes) |
| **Java JRE/JDK** | 1.6+ |
| **Python** | 3.7+ |

Java is required because CMake automatically invokes the bundled ANTLR 4 JAR to generate the lexer and parser C++ sources from the `.g4` grammar files during the build. No separate grammar-regeneration step is needed.

Python is required for running the test suite. Install Python dependencies with:

```
pip install -r requirements.txt
```

### Platform Notes

| Platform | Minimum Compiler |
|---|---|
| **Linux** | GCC 10 or Clang 15 |
| **macOS** | Apple Clang 15 |
| **Windows** | MSVC (Visual Studio 2019) |

Note: Linux required uuid-dev (e.g. `sudo apt-get install uuid-dev`)

ARM and x64 toolchain files are provided under `Platform/<os>/Toolchain/` for cross-compilation scenarios.

## Building

AZSLC uses a standard CMake workflow. All external dependencies are fetched automatically via `FetchContent` -- no manual dependency installation is needed beyond the prerequisites listed above.

### Quick Start

```bash
# Configure
cmake -B Build

# Build
cmake --build Build --config Release
```

The compiled binary is output to `Build/bin/`.

### Common CMake Options

| Option | Default | Description |
|---|---|---|
| `BUILD_TESTING` | `ON` | Enable building and registering CTest tests |

### IDE Support

On Windows, CMake generates a Visual Studio solution. The `azslc` project is set as the startup project automatically. You can also open the folder directly in Visual Studio or VS Code with CMake extensions.

```bash
# Generate and open in Visual Studio
cmake -B Build -S . -G "Visual Studio 17 2022"
start Build/azslc.sln
```

## Testing

AZSLC uses **CTest** to run its test suite. Tests are automatically registered when `BUILD_TESTING` is `ON` (the default).

### Running Tests

```bash
# Run all tests
ctest --test-dir Build

# Run tests in parallel
ctest --test-dir Build -j8

# Run only a specific category
ctest --test-dir Build -L Advanced
ctest --test-dir Build -L Emission
ctest --test-dir Build -L Samples
ctest --test-dir Build -L Semantic
ctest --test-dir Build -L Syntax

# Run a specific test by name
ctest --test-dir Build -R "Syntax/empty"

# Verbose output (shows PASS/FAIL details)
ctest --test-dir Build --output-on-failure
```

### Test Categories

| Category | Description |
|---|---|---|
| **Advanced** | Complex Python-driven test scripts |
| **Emission** | Compiles and verifies emitted output against expected patterns |
| **Samples** | Full compilation of sample shaders |
| **Semantic** | Validates semantic analysis |
| **Syntax** | Validates AZSL grammar parsing |

Each category includes tests that are expected to pass **and** tests that are expected to fail (located in `AsError`/`AsErrors` subdirectories). Error tests verify that the compiler correctly rejects invalid input.

See [Tests/README.md](Tests/README.md) for detailed information on the test conventions and how to add new tests.

## How the Grammar is Generated

The AZSL grammar is defined by two ANTLR 4 files:

- `Source/Grammar/azslLexer.g4` - Lexer rules (tokens, keywords, types)
- `Source/Grammar/azslParser.g4` - Parser rules (compilation units, declarations, expressions)

During the CMake build, the bundled ANTLR 4 JAR is invoked via Java to generate C++ lexer/parser sources. This happens automatically as a build step. If you modify a `.g4` file, the generated sources are rebuilt on the next build.

## External Dependencies

All dependencies are fetched automatically by CMake at configure time:

| Library | Description |
|---|---|
| [ANTLR4 C++ Runtime](https://github.com/antlr/antlr4) | Parser runtime |
| [CLI11](https://github.com/CLIUtils/CLI11) | Command-line argument parsing |
| [JsonCpp](https://github.com/open-source-parsers/jsoncpp) | JSON output for reflection data |

## Project Structure

```
CMakeLists.txt              Root build file
CMake/                      CMake modules
External/                   Third-party dependency declarations
Documentation/              Language and usage documentation
Platform/                   Platform-specific code and toolchains
Source/                     Compiler source code
  Grammar/                  ANTLR4 .g4 grammar files
  AzslcMain.cpp             Entry point
  AzslcEmitter.*            HLSL code emitter
  AzslcListener.*           ANTLR parse-tree listener
Tests/                      Test suite (CTest-driven)
  Advanced/                 Complex Python-driven tests
  Emission/                 Output verification tests
  Samples/                  Full compilation tests
  Semantic/                 Semantic analysis tests
  Syntax/                   Syntax validation tests
```

## Further Reading

- [how-to.md](Documentation/how-to.md) - Detailed command-line usage and output format reference
- [grammar.md](Documentation/grammar.md) - AZSL language specification and grammar notes
- [Tests](Tests/README.md) - Test conventions and how to add new tests

## License

For terms please see the LICENSE\*.TXT files at the root of this distribution.
