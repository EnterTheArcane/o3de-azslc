#
# Copyright (c) Contributors to the Open 3D Engine Project.
# For complete copyright and license terms please see the LICENSE at the root of this distribution.
#
# SPDX-License-Identifier: Apache-2.0 OR MIT
#
#

include_guard(GLOBAL)

azslc_option(AZSLC_CODE_SIGNING OFF BOOL "Enable code signing")

if(NOT AZSLC_CODE_SIGNING)
    return()
endif()

if(APPLE)
    # For release, pass a real identity, e.g. "Developer ID Application: <Team Name> (<Team ID>)"
    # Use "-" for ad-hoc (self-signed) during development.
    azslc_option(AZSLC_CODESIGN_IDENTITY "-" STRING
        "macOS code signing identity")

    # Hardened runtime is required for notarization.
    azslc_option(AZSLC_CODESIGN_HARDENED_RUNTIME OFF BOOL
        "Enable hardened runtime")

    # Optionally specify an entitlements file for additional permissions (e.g. hardened runtime exceptions).
    azslc_option(AZSLC_CODESIGN_ENTITLEMENTS "" PATH
        "Path to an entitlements plist file")

    # Explicit path to the codesign executable
    azslc_option(AZSLC_CODESIGN_PATH "" PATH
        "Path to codesign")

    if(AZSLC_CODESIGN_PATH)
        set(sign_tool ${AZSLC_CODESIGN_PATH})
    else()
        find_program(sign_tool codesign REQUIRED)
    endif()

    set(sign_args
        --force
        --sign "${AZSLC_CODESIGN_IDENTITY}"
    )

    if(NOT AZSLC_CODESIGN_IDENTITY STREQUAL "-")
        list(APPEND sign_args --timestamp)
    endif()

    if(AZSLC_CODESIGN_HARDENED_RUNTIME)
        list(APPEND sign_args --options runtime)
    endif()

    if(AZSLC_CODESIGN_ENTITLEMENTS)
        list(APPEND sign_args --entitlements "${AZSLC_CODESIGN_ENTITLEMENTS}")
    endif()

    set_property(GLOBAL PROPERTY _AZSLC_SIGN_TOOL "${sign_tool}")
    set_property(GLOBAL PROPERTY _AZSLC_SIGN_ARGS "${sign_args}")

    message(STATUS "macOS code signing enabled (identity: ${AZSLC_CODESIGN_IDENTITY})")
endif()

if(WIN32)
    # At minimum, provide a certificate thumbprint.
    # The certificate is typically installed in the Windows certificate store beforehand.
    azslc_option(AZSLC_SIGNTOOL_THUMBPRINT "" STRING
        "SHA-1 certificate thumbprint for Windows code signing")

    # Use an RFC 3161 timestamp server to ensure signatures remain valid after the certificate expires.
    azslc_option(AZSLC_SIGNTOOL_TIMESTAMP_URL "https://timestamp.digicert.com" STRING
        "RFC 3161 timestamp server url for Windows code signing")

    # Explicit path to the signtool utility
    azslc_option(AZSLC_SIGNTOOL_PATH "" PATH
        "Path to signtool")

    if(NOT AZSLC_SIGNTOOL_THUMBPRINT)
        message(FATAL_ERROR "AZSLC_CODE_SIGNING is enabled but AZSLC_SIGNTOOL_THUMBPRINT is not set")
    endif()

    if(AZSLC_SIGNTOOL_PATH)
        set(sign_tool ${AZSLC_SIGNTOOL_PATH})
    else()
        find_program(sign_tool signtool REQUIRED)
    endif()

    set(sign_args
        sign
        /sha1 "${AZSLC_SIGNTOOL_THUMBPRINT}"
        /fd SHA256
    )

    if(AZSLC_SIGNTOOL_TIMESTAMP_URL)
        list(APPEND sign_args
            /tr "${AZSLC_SIGNTOOL_TIMESTAMP_URL}"
            /td SHA256
        )
    endif()

    set_property(GLOBAL PROPERTY _AZSLC_SIGN_TOOL "${sign_tool}")
    set_property(GLOBAL PROPERTY _AZSLC_SIGN_ARGS "${sign_args}")

    message(STATUS "Windows code signing enabled (thumbprint: ${AZSLC_SIGNTOOL_THUMBPRINT})")
endif()

add_custom_target(sign ALL)

function(azslc_sign_target target)
    if(NOT TARGET "${target}")
        message(WARNING "Cannot sign target: ${target} (does not exist)")
        return()
    endif()

    get_target_property(target_type ${target} TYPE)
    set(supported_target_types "EXECUTABLE;SHARED_LIBRARY;MODULE_LIBRARY")
    if(NOT target_type IN_LIST supported_target_types)
        message(WARNING "Cannot sign target: ${target} (unsupported target type: ${target_type})")
        return()
    endif()

    get_target_property(target_imported ${target} IMPORTED)
    if(target_imported)
        return()
    endif()

    get_property(sign_tool GLOBAL PROPERTY _AZSLC_SIGN_TOOL)
    get_property(sign_args GLOBAL PROPERTY _AZSLC_SIGN_ARGS)

    add_dependencies(sign ${target})
    add_custom_command(
        TARGET sign POST_BUILD
        COMMAND "${sign_tool}" ${sign_args} "$<TARGET_FILE:${target}>"
        COMMENT "Signing ${target}"
        VERBATIM
    )
endfunction()
