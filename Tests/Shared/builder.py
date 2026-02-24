#!/usr/bin/python
# -*- coding: utf-8 -*-
"""
Copyright (c) Contributors to the Open 3D Engine Project.
For complete copyright and license terms please see the LICENSE at the root of this distribution.

SPDX-License-Identifier: Apache-2.0 OR MIT
"""

import io
import os
import os.path
import platform
import subprocess
import sys
from typing import List, Optional

from . import compiler
from .colors import *

# Cached path to the SPIRV-Cross binary (populated by find_dxc_and_spirv_cross).
_spirv_cross_path: str = ""


class BuildResult:
    """Outcome of a shader build attempt."""

    def __init__(self, can_build: bool, did_build: bool) -> None:
        self.can_build = can_build
        self.did_build = did_build


BUILD_IMPOSSIBLE = BuildResult(False, False)
BUILD_FAILED = BuildResult(True, False)
BUILD_SUCCEEDED = BuildResult(True, True)


def find_dxc(silent: bool) -> Optional[str]:
    """Return the path to the Windows 10 version of dxc, or None."""
    if not (platform.system() == "Windows" and platform.release() == "10"):
        if not silent:
            print(
                Foreground.YELLOW + Style.BRIGHT +
                "Will only test dxc.exe on Windows 10." + Style.RESET_ALL
            )
        return None

    windows_kits_dir = os.path.expandvars(
        "%ProgramFiles(x86)%/Windows Kits/10/bin"
    )
    if not os.path.isdir(windows_kits_dir):
        if not silent:
            print(
                Foreground.YELLOW + Style.BRIGHT +
                "Expected {}, but did not find it.".format(windows_kits_dir) +
                Style.RESET_ALL
            )
        return None

    dxc_locations: List[str] = []
    for root, dirs, files in os.walk(windows_kits_dir):
        if "dxc.exe" in files and root.find("x64") >= 0:
            dxc_locations.append(os.path.join(root, "dxc.exe"))
    dxc_locations.sort(reverse=True)

    return dxc_locations[0] if dxc_locations else None


def find_spirv_cross(
    spirv_cross_path: str, silent: bool
) -> Optional[str]:
    """Return the path to the SPIRV-Cross binary, or None."""
    if not os.path.isdir(spirv_cross_path):
        if not silent:
            print(
                Foreground.YELLOW + Style.BRIGHT +
                "Expected {}, but did not find it.".format(spirv_cross_path) +
                Style.RESET_ALL
            )
        return None

    spirv_locations: List[str] = []
    for root, dirs, files in os.walk(spirv_cross_path):
        if os.name == 'nt':
            if "spirv-cross.exe" in files and root.find("win_x64") >= 0:
                spirv_locations.append(os.path.join(root, "spirv-cross.exe"))
        elif os.name == 'posix':
            if "spirv-cross" in files and root.find("darwin_x64") >= 0:
                spirv_locations.append(os.path.join(root, "spirv-cross"))
    spirv_locations.sort(reverse=True)

    return spirv_locations[0] if spirv_locations else None


def find_dxc_and_spirv_cross(
    silent: bool,
    az_dxc_path: Optional[str],
    need_spirv_cross: bool
) -> Optional[str]:
    """Locate DXC (and optionally SPIRV-Cross), caching the SPIRV path.

    Returns the path to the DXC compiler, or None if it cannot be found.
    """
    global _spirv_cross_path
    if az_dxc_path is not None:
        _spirv_cross_path = find_spirv_cross(
            az_dxc_path + "/SPIRVCross/", silent
        )
        if need_spirv_cross and _spirv_cross_path is None:
            if not silent:
                print(
                    Foreground.YELLOW + Style.BRIGHT +
                    "SPIRV not found. *Assuming* we shouldn't test it "
                    "on this platform." + Style.RESET_ALL
                )
            return None

        if platform.system() == "Windows":
            az_dxc_path += "/DirectXShaderCompiler/3.0.0-az/bin/win_x64/Release/dxc.exe"
        elif platform.system() == "Darwin":
            az_dxc_path += "/DirectXShaderCompiler/3.0.0-az/bin/darwin_x64/Release/bin/dxc"
        elif platform.system() == "Linux":
            az_dxc_path += "/DirectXShaderCompiler/3.0.0-az/bin/linux_x64/Release/bin/dxc"
        else:
            return None

    if az_dxc_path and os.path.exists(az_dxc_path):
        dxc_path = az_dxc_path
    else:
        dxc_path = find_dxc(silent)
        if need_spirv_cross:
            print(
                Foreground.YELLOW + Style.BRIGHT +
                "Windows' DXC does not support SPIRV. skipping." +
                Style.RESET_ALL
            )
            return None

    if dxc_path is None:
        if not silent:
            print(
                Foreground.YELLOW + Style.BRIGHT +
                "DXC not found. *Assuming* we shouldn't test it "
                "on this platform." + Style.RESET_ALL
            )
        return None
    return dxc_path


def build_dxc(
    thefile: str,
    compiler_path: str,
    silent: bool,
    extra_inc_list: List[str],
    azslc_args: List[str],
    dxc_args: List[str],
    az_dxc_path: Optional[str],
    out_format: str,
    need_spirv_cross: bool
) -> BuildResult:
    """Compile an AZSL file through AZSLC then DXC for vertex and pixel shaders.

    Returns a BuildResult indicating the outcome.
    """
    dxc_path = find_dxc_and_spirv_cross(silent, az_dxc_path, need_spirv_cross)
    if dxc_path is None:
        return BUILD_IMPOSSIBLE

    in_file, _ = os.path.splitext(thefile)
    code_out = in_file + ".hlsl"
    sbin_vs = in_file + "VS." + out_format
    sbin_ps = in_file + "PS." + out_format

    for path in (code_out, sbin_vs, sbin_ps):
        if os.path.exists(path):
            os.remove(path)

    stdout, ok = compiler.build_and_get(
        thefile, compiler_path, silent, azslc_args
    )
    if not ok:
        if not silent:
            print(
                Foreground.RED + Style.BRIGHT +
                "Failed to generate .hlsl file with AZSLC." + Style.RESET_ALL
            )
        return BUILD_FAILED

    # Prepend extra includes then write the generated HLSL.
    with open(code_out, "wb+") as f:
        for inc_file in extra_inc_list:
            full_inc = os.path.join(os.path.dirname(thefile), inc_file)
            if not os.path.exists(full_inc):
                if not silent:
                    print(
                        Foreground.RED + Style.BRIGHT +
                        "Include file {} doesn't exist!".format(full_inc) +
                        Style.RESET_ALL
                    )
                return BUILD_FAILED
            with open(full_inc, "rb") as f_in:
                f.write(f_in.read())
        f.write(stdout)

    # Compile vertex shader
    dxc_compile_args = (
        [dxc_path, "-T", "vs_6_2", "-E", "MainVS"] +
        dxc_args + ["-Fo", sbin_vs, code_out]
    )
    process = subprocess.Popen(
        dxc_compile_args, stdout=subprocess.PIPE, stderr=subprocess.PIPE
    )
    _, err = process.communicate()
    if not silent:
        sys.stderr.write(err.decode('utf-8'))
    if not os.path.exists(sbin_vs):
        if not silent:
            print(
                Foreground.RED + Style.BRIGHT +
                "Failed to compile MainVS with dxc.exe(for " +
                out_format + ")." + Style.RESET_ALL
            )
        return BUILD_FAILED

    # Compile pixel shader
    dxc_compile_args = (
        [dxc_path, "-T", "ps_6_2", "-E", "MainPS"] +
        dxc_args + ["-Fo", sbin_ps, code_out]
    )
    process = subprocess.Popen(
        dxc_compile_args, stdout=subprocess.PIPE, stderr=subprocess.PIPE
    )
    _, err = process.communicate()
    if not silent:
        sys.stderr.write(err.decode('utf-8'))
    if not os.path.exists(sbin_ps):
        if not silent:
            print(
                Foreground.RED + Style.BRIGHT +
                "Failed to compile MainPS with dxc.exe(for " +
                out_format + ")." + Style.RESET_ALL
            )
        return BUILD_FAILED

    return BUILD_SUCCEEDED


def build_dxc_compute(
    thefile: str,
    compiler_path: str,
    silent: bool,
    extra_inc_list: List[str],
    azslc_args: List[str],
    dxc_args: List[str],
    az_dxc_path: Optional[str],
    out_format: str,
    need_spirv_cross: bool
) -> BuildResult:
    """Compile an AZSL file through AZSLC then DXC for compute shaders.

    Returns a BuildResult indicating the outcome.
    """
    dxc_path = find_dxc_and_spirv_cross(silent, az_dxc_path, need_spirv_cross)
    if dxc_path is None:
        return BUILD_IMPOSSIBLE

    if not silent:
        print(
            Foreground.CYAN + Style.BRIGHT +
            "Testing DXC compilation for compute shaders." + Style.RESET_ALL
        )

    in_file, _ = os.path.splitext(thefile)
    code_out = in_file + ".hlsl"
    sbin_cs = in_file + "CS." + out_format

    for path in (code_out, sbin_cs):
        if os.path.exists(path):
            os.remove(path)

    stdout, ok = compiler.build_and_get(
        thefile, compiler_path, silent, azslc_args
    )
    if not ok:
        if not silent:
            print(
                Foreground.RED + Style.BRIGHT +
                "Failed to generate .hlsl file with AZSLC." + Style.RESET_ALL
            )
        return BUILD_FAILED

    # Prepend extra includes then write the generated HLSL.
    with open(code_out, "wb+") as f:
        for inc_file in extra_inc_list:
            full_inc = os.path.join(os.path.dirname(thefile), inc_file)
            if not os.path.exists(full_inc):
                if not silent:
                    print(
                        Foreground.RED + Style.BRIGHT +
                        "Include file {} doesn't exist!".format(full_inc) +
                        Style.RESET_ALL
                    )
                return BUILD_FAILED
            with io.open(full_inc, "r", encoding="latin-1") as f_in:
                f.write(f_in.read())
        f.write(stdout)

    # Compile compute shader
    dxc_compile_args = (
        [dxc_path, "-T", "cs_6_2", "-E", "MainCS"] +
        dxc_args + ["-Fo", sbin_cs, code_out]
    )
    process = subprocess.Popen(
        dxc_compile_args, stdout=subprocess.PIPE, stderr=subprocess.PIPE
    )
    _, err = process.communicate()
    if not silent:
        sys.stderr.write(err.decode('utf-8'))
    if not os.path.exists(sbin_cs):
        if not silent:
            print(
                Foreground.RED + Style.BRIGHT +
                "Failed to compile MainCS with dxc.exe." + Style.RESET_ALL
            )
        return BUILD_FAILED

    if not silent:
        print(
            Foreground.GREEN + Style.BRIGHT +
            "[" + thefile + "." + out_format + "]" + Style.RESET_ALL
        )
    return BUILD_SUCCEEDED
