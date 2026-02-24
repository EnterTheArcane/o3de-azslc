# AZSLC HowTo guide

`TL;DR` Use azslc from command line (or process invoke) to translate .azsl files to .hlsl, or extract data such as symbols reference table, shader resource table layout, input assembly layout and output merger layout.

Version `AZSL Compiler 1.9.0` will display the following with the `azslc --help` command:

```
Amazon Shader Language Compiler

POSITIONALS:
  FILE TEXT                   Input file (pass - to read from stdin). 

OPTIONS:
  -h,     --help              Print this help message and exit 
          --version           Prints version information 
  -o TEXT                     Output file (writes to stdout if omitted). 
          --unique-idx        Use unique indices for all registers. e.g. b0, t0, u0, s0 becomes 
                              b0, t1, u2, s3. Use on platforms that don't differentiate 
                              registers by resource type. 
          --cb-body           Emit ConstantBuffer body rather than using <T>. 
          --root-sig          Emit RootSignature for parameter binding in the shader. 
                              --namespace must also be used to select a specific API. 
          --root-const INT    Maximum size in bytes of the root constants buffer. 
          --pad-root-const    Automatically append padding data to the root constant CB to keep 
                              it aligned to a 16-byte boundary. 
          --Zpc               Pack matrices in column-major order (default). Cannot be 
                              specified together with -Zpr. 
          --Zpr               Pack matrices in row-major order. Cannot be specified together 
                              with -Zpc. 
          --pack-dx12         Pack buffers using strict DX12 packing rules. If not specified 
                              AZSLC will use relaxed packing rules. 
          --pack-vulkan       Pack buffers using strict Vulkan packing rules (Vector-relaxed 
                              std140 for uniforms and std430 for storage buffers). 
          --pack-opengl       Pack buffers using strict OpenGL packing rules (Vector-strict 
                              std140 for uniforms and std430 for storage buffers). 
          --namespace TEXT ... 
                              Activate an attribute namespace. May be used multiple times to 
                              activate multiple namespaces. Activating a namespace may also 
                              activate corresponding API-specific features, like dx for DirectX 
                              12, vk for Vulkan, and mt for Metal. 
          --ia                Output a list of vs entries with their Input Assembler layouts 
                              *and* a list of CS entries and their numthreads. 
          --om                Output the Output Merger layout instead of the shader code. 
          --srg               Output the Shader Resource Group layout instead of the shader 
                              code. 
          --options           Output the list of available shader options for this shader. 
          --dumpsym           Dump symbols. 
          --syntax            Check syntax (no output means no complaints). 
          --semantic          Check semantics (no output means no complaints). 
          --ast               Output the abstract syntax tree. 
          --bindingdep        Output binding dependencies (what entry points access what 
                              external resources). 
          --visitsym TEXT     Output the locations of all relationships of the supplied symbol 
                              name. 
          --full              Output the shader code, IA layout, OM layout, SRG layout, the 
                              list of available shader options, and the binding dependencies. 
          --strip-unused-srgs Strips unused SRGs. 
          --no-ms             Transforms usage of Texture2DMS/Texture2DMSArray and related 
                              functions and semantics into plain Texture2D/Texture2DArray 
                              equivalents. This is useful for allowing shader authors to easily 
                              write AZSL code that can be compiled into alternatives to work 
                              with both a multisample render pipeline and a non-MS render 
                              pipeline. 
          --no-alignment-validation 
                              Skips checking for potential alignment issues related to 
                              differences between dxil and spirv.By default, potential 
                              alignment discrepancies will fail compilation. 
  -d                          (Option of --visitsym) Visit direct references. 
  -v                          (Option of --visitsym) Visit overload-set. 
  -f                          (Option of --visitsym) Visit family. 
  -r                          (Option of --visitsym) Visit recursively. 
          --listpredefined    Output a list of all predefined types in AZSLang. 
          --max-spaces INT    Will choose register spaces that do not extend past this limit. 
          --sc-options        Use specialization constants for shader options. 
          --no-subpass-input  Transform usage of SubpassInput/SubpassInputMS into 
                              Texture2D/Texture2DMS 
          --subpass-input-offset INT 
                              Offset to apply to the subpass index attribute. 
          --W0                Suppresses all warnings. 
          --W1                Activate severe warnings (default). 
          --W2                Activate warnings that may be significant. 
          --W3                Activate low-confidence diagnostic warnings. 
          --Wx                Treat activated warnings as errors. 
          --Wx1               Treat level-1 warnings as errors. 
          --Wx2               Treat level-2 and below warnings as errors. 
          --Wx3               Treat level-3 and below warnings as errors. 
          --min-descriptors TEXT 
                              Comma-separated list of limits corresponding to 
                              <set,space,sampler,texture,buffer> descriptors. Emits a warning 
                              if a count overshoots a limit. Use -1 to specify "no limit". 
          --verbose           
```

# Commands breakdown

## General usage

Invoking `azslc` from command line requires either a file input (`.azsl` file written in the AZSL grammar) or the `stdin` (use the `-` option). It will transpile the AZSL shader code to HLSL code and it will emit it to the standard output.

Everything else is optional.

For most outputs, specifying `-o OUTFILE` redirects the code emission to a file. Warnings and errors still use the standard error output and verbose messages use the standard output.

## Generate shader source

### `--use-spaces`

When specified, all shader resources (data views, constant buffers and sampler states) will be grouped using logical space indices. The logical space index for each group is based on the resource's Shader Resource Group.
(All resources from the same SRG share the same logical space index.)

This option must be specified for DirectX 12 and Vulkan.

### `--cb-body`

This feature exists for legacy shaders support only (Shader Model 5). When compiling with dxc it shouldn't be used.

All constant buffers will use
```
cbuffer MyBuffer
{
  // Body
};
```
rather than
```
struct MyStruct
{
  // Body
};

ConstantBuffer<MyStruct> MyBuffer;
```

### `--root-sig`

Emits a RootSignature with the fixed name `sig` in the shader code for all resources, or the equivalent binding root for different graphic API.
This argument also requires the `--namespace=<nspc>` argument. For example passing `--namespace=dx` together will emit a DirectX 12 style RootSignature.

This argument only emits the struct, but it will not modify any other code. The signature must be specified when compiling with `dxc.exe` by using `rootsig-define sig -extractrootsignature` (if compiling for DirectX12) otherwise it will have no effect on the shader code. Note that the name has to match.

### Examples per platform

#### DirectX 12

Use `azslc Shader.azsl --namespace=dx --use-spaces -o Shader.hlsl`

#### Vulkan

Use `azslc Shader.azsl --namespace=vk --use-spaces --unique-idx -o Shader.hlsl`

#### Metal

Use `azslc Shader.azsl --namespace=mt --use-spaces --unique-idx -o Shader.hlsl`

## `--semantic`

Only performs semantic check of the code, no shader generation. No output if correct, use `--verbose` if you want to printout compiler internals trace data.

## `--syntax`

Only performs syntax check of the code, no shader generation. Ends with a 0 process code if the program is valid.

## `--dumpsym`

Dumps the symbol table to the standard output in yaml format.

It prints the symbol name, **all** lines which reference it, where is it declared and members (functions, variables, attributes, etc.).
```
Symbol /ExampleSRG:
  kind: ShaderResourceGroup
  references:
    - {line: 71, col: 29}
    - {line: 86, col: 29}
    - {line: 92, col: 24}
    - {line: 92, col: 55}
    - {line: 94, col: 24}
    - {line: 94, col: 67}
  line: 7
  structs: JustForPacking, ModelStruct, UserStruct,
  srViews: m_diffuseMap, m_bufferView1, m_bufferView2, m_bufferView4, m_bufferView5, m_bufferView6, m_bufferView7, m_bufferView8a,
  samplers: m_sampler,
  CBs: m_modelConstants, m_arrayOfFour,
Symbol /ExampleSRG/JustForPacking:
  kind: Struct
  references:
    - {line: 26, col: 20}
  line: 9
  members:
    - {kind: Variable, name: /ExampleSRG/JustForPacking/m_somePair}
    - {kind: Variable, name: /ExampleSRG/JustForPacking/m_columnMatrix}
    - {kind: Variable, name: /ExampleSRG/JustForPacking/m_someVector}
    - {kind: Variable, name: /ExampleSRG/JustForPacking/m_someScalar}
    - {kind: Variable, name: /ExampleSRG/JustForPacking/m_rowMatrix}
```

## `--ia` InputAssembly layout

Emits the InputAssembler layout instead of the shader source. The data is a Json array which lists all functions in the shader eligible for vertex shader entry point.

```json
{
  "entry": "MainVS",
  "streams": [
    {
      "baseType": "float", // Base type of the arithmetic type
      "cols": 3, // 0 if scalar, element count if vector, column count if matrix
      "dimensions": [], // List of dimensions if array or multiarray
      "fullType": "?float3", // The arithmetic type as declared
      "name": "m_position", // Name of the attribute
      "rows": 0, // 0 if scalar or vector, row count if matrix
      "semanticIndex": 0, // Semantic index
      "semanticName": "POSITION", // Semantic name without the index
      "systemValue": false // Is the semantic a system value
    }
  ]
}
```

## `--om` OutputMerger layout

Emits the OutputMerger layout instead of the shader source. The data is a Json array which lists all functions in the shader eligible for pixel shader entry point.

```json
{
  "entry": "MainPS",
  "renderTargets": [
    {
      "baseType": "float", // Base type of the arithmetic type
      "cols": 4, // 0 if scalar, element count if vector (cannot be a matrix)
      "format": "R8G8B8A8_UNORM", // Format of the render target (hint)
      "semanticIndex": 0, // Semantic index
      "semanticName": "SV_Target" // Semantic name without the index
    }
  ]
}
```

## `--srg` ShaderResourceGroup layout

Emits the ShaderResourceGroup layout instead of the shader source. The data is a Json array which lists all resource data (data views, samplers and constant buffer packing data) in each SRG.

```json
{
  "bindingSlot": 0,
  "id": "ExampleSRG",

  "inputsForBufferViews": [
    {
      "count": 4, // Number of views (1 if not an array)
      "id": "m_myBuffer", // Name of the buffer view
      "type": "ConstantBuffer<MyStruct>", // Type of the buffer
      "usage": "Read" // Read or ReadWrite
    }
  ],

  "inputsForImageViews": [
    {
      "count": 1,
      "id": "m_diffuseMap",
      "type": "Texture2D",
      "usage": "Read"
    }
  ],

  "inputsForSRGConstants": [
    {
      "constantByteOffset": 0, // Offset of the element
      "constantByteSize": 64, // Size of the element
      "constantId": "m_myModel.m_modelToWorld",
      "qualifiedName": "/ExampleSRG/ModelStruct/m_modelToWorld",
      "typeDimensions": [], // Dimensions if array or multiarray
      "typeKind": "Predefined", // Predefined or Struct
      "typeName": "?float4x4"
    },
    {
      "constantByteOffset": 0, // Structs share offset with their first element
      "constantByteSize": 64, // Combined size of all elements
      "constantId": "m_myModel",
      "qualifiedName": "/ExampleSRG/m_myModel",
      "typeDimensions": [],
      "typeKind": "Struct",
      "typeName": "/ExampleSRG/ModelStruct"
    },
    {
      "constantByteOffset": 64,
      "constantByteSize": 32, // float2x3 row-major: 2 rows x 16 bytes = 32 bytes
      "constantId": "m_rowMajorConst",
      "qualifiedName": "/ExampleSRG/m_rowMajorConst",
      "typeDimensions": [],
      "typeKind": "Predefined",
      "typeName": "?float2x3"
    },
    {
      "constantByteOffset": 96,
      "constantByteSize": 48, // float2x3 column-major: 3 cols x 16 bytes = 48 bytes
      "constantId": "m_colMajorConst",
      "qualifiedName": "/ExampleSRG/m_colMajorConst",
      "typeDimensions": [],
      "typeKind": "Predefined",
      "typeName": "?float2x3"
    }
  ],

  "inputsForSamplers": [
    {
      "addressU": "TEXTURE_ADDRESS_WRAP",
      "addressV": "TEXTURE_ADDRESS_WRAP",
      "addressW": "TEXTURE_ADDRESS_WRAP",
      "anisotropyEnable": true,
      "anisotropyMax": 16,
      "borderColor": "STATIC_BORDER_COLOR_TRANSPARENT_BLACK",
      "comparisonFunc": "COMPARISON_ALWAYS",
      "filterMag": "Point",
      "filterMin": "Point",
      "filterMip": "Point",
      "isComparison": false,
      "mipLodBias": 0,
      "mipLodMax": 15,
      "mipLodMin": 0,
      "reductionType": "Filter"
    }
  ]
}
```


## `--ast` Abstract Syntax Tree

This option will print out on stdout a lisp-like tree of the syntax of an input program as parsed by AntlR.
Example:
`azslc --ast Tests/Syntax/comma-separated-declarators.azsl`

```
(compilationUnit
  (topLevelDeclaration
    (variableDeclarationStatement
      (variableDeclaration storageFlags
        (type
          (predefinedType
            (scalarType int)
          )
        )

        (variableDeclarators
          (variableDeclarator a)
          ,
          (variableDeclarator b)
        )
      )
      ;)
  )
<EOF>)
```
