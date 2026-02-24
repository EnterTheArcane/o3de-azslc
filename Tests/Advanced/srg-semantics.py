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


def verify_ia_semantics(thefile, compiler_path, silent):
    j, ok = compiler.build_and_get_json(thefile, compiler_path, silent, ["--ia"])

    if ok:
        predicates = []

        predicates.append(lambda: j["inputLayouts"][0]["streams"][0]["systemValue"] == 0)
        predicates.append(lambda: j["inputLayouts"][0]["streams"][1]["systemValue"] == 0)
        predicates.append(lambda: j["inputLayouts"][1]["streams"][0]["systemValue"] == 1)  # SV_POSITION
        predicates.append(lambda: j["inputLayouts"][1]["streams"][1]["systemValue"] == 0)

        if not silent: print(Foreground.CYAN + Style.BRIGHT + "input assembler semantics verification..." + Style.RESET_ALL)
        ok = compiler.verify_all_predicates(predicates, j)
    return True if ok else False


def verify_om_semantics(thefile, compiler_path, silent):
    j, ok = compiler.build_and_get_json(thefile, compiler_path, silent, ["--om"])

    if ok:
        predicates = []

        predicates.append(lambda: j["outputLayouts"][0]["renderTargets"][0]["systemValue"] == 1)  # SV_TARGET

        if not silent: print(Foreground.CYAN + Style.BRIGHT + "input assembler semantics verification..." + Style.RESET_ALL)
        ok = compiler.verify_all_predicates(predicates, j)
    return True if ok else False


result = 0  # to define for subtests
result_failed = 0


def do_tests(compiler, silent):
    global result
    global result_failed

    # Working directory should have been set to this script's directory by the calling parent
    # You can get it once do_tests() is called, but not during initialization of the module,
    #  because at that time it will still be set to the working directory of the calling script
    work_dir = os.getcwd()

    if verify_ia_semantics(os.path.join(work_dir, "srg-semantics.azsl"), compiler, silent):
        result += 1
    else:
        result_failed += 1

    if verify_om_semantics(os.path.join(work_dir, "srg-semantics.azsl"), compiler, silent):
        result += 1
    else:
        result_failed += 1


if __name__ == "__main__":
    assert "please call from runner.py"
