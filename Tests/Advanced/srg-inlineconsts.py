#!/usr/bin/python
# -*- coding: utf-8 -*-
"""
Copyright (c) Contributors to the Open 3D Engine Project.
For complete copyright and license terms please see the LICENSE at the root of this distribution.

SPDX-License-Identifier: Apache-2.0 OR MIT
"""

import os

from Shared import compiler
from Shared.colors import *


def verify_ok(thefile, compiler_path, silent):
    j, ok = compiler.build_and_get_json(thefile, compiler_path, silent, ["--srg", "--root-const=52"])

    if ok:
        predicates = []

        predicates.append(lambda: j["RootConstantBuffer"]["bufferForRootConstants"]["count"] == 1)
        predicates.append(lambda: j["RootConstantBuffer"]["bufferForRootConstants"]["index"] == 0)
        predicates.append(lambda: j["RootConstantBuffer"]["bufferForRootConstants"]["space"] == 0)
        predicates.append(lambda: j["RootConstantBuffer"]["bufferForRootConstants"]["usage"] == "Read")
        predicates.append(lambda: j["RootConstantBuffer"]["bufferForRootConstants"]["sizeInBytes"] == 60)
        predicates.append(lambda: j["RootConstantBuffer"]["bufferForRootConstants"]["id"] == "Root_Constants")

        if not silent: print(Foreground.CYAN + Style.BRIGHT + "inline const verification..." + Style.RESET_ALL)
        ok = compiler.verify_all_predicates(predicates, j)
    return True if ok else False


def verify_zero_ok(thefile, compiler_path, silent):
    j, ok = compiler.build_and_get_json(thefile, compiler_path, silent, ["--srg", "--root-const=0"])

    return True if ok else False


def verify_zero_fails(thefile, compiler_path, silent):
    j, ok = compiler.build_and_get_json(thefile, compiler_path, silent, ["--srg", "--root-const=0"])

    return True if not ok else False


result = 0  # to define for subtests
result_failed = 0


def do_tests(compiler, silent):
    global result
    global result_failed

    # Working directory should have been set to this script's directory by the calling parent
    # You can get it once do_tests() is called, but not during initialization of the module,
    #  because at that time it will still be set to the working directory of the calling script
    work_dir = os.getcwd()

    if verify_ok(os.path.join(work_dir, "srg-inlineconsts.azsl"), compiler, silent):
        result += 1
    else:
        result_failed += 1

    if verify_zero_ok(os.path.join(work_dir, "simple.azsl"), compiler, silent):
        result += 1
    else:
        result_failed += 1

    if verify_zero_fails(os.path.join(work_dir, "srg-inlineconsts.azsl"), compiler, silent):
        result += 1
    else:
        result_failed += 1


if __name__ == "__main__":
    assert "please call from runner.py"
