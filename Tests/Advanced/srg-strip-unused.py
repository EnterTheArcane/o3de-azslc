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

'''
Validates the functionality of the --strip-unused-srgs flag.
'''


def test_unused_srg_stripping(thefile, compiler_path, silent, alive_srgs, dead_srgs):
    # First we compile as is, no srg stripping, and make sure all SRGs are present in the symbol output
    symbols, ok = compiler.build_and_get_symbols(thefile, compiler_path, silent)
    if ok:
        if not silent: print(Foreground.CYAN + Style.BRIGHT + "testUnusedSrgStripping: Verifying no srg was stripped..." + Style.RESET_ALL)
        predicates = []
        for srgName in alive_srgs + dead_srgs:
            symbol_expr = "Symbol '{}'".format(srgName)
            predicates.append(lambda symbol_expr=symbol_expr: symbols[symbol_expr]['kind'] == 'ShaderResourceGroup')
        ok = compiler.verify_all_predicates(predicates, symbols, silent)
        if ok and not silent:
            print(Style.BRIGHT + "OK! " + str(len(predicates)) + " testUnusedSrgStripping: Verified no srg was stripped." + Style.RESET_ALL)
    else:
        return False
    # Now we force unused srg removal and make sure that only @aliveSrgs are present and all @deadSrgs got removed.
    symbols, ok = compiler.build_and_get_symbols(thefile, compiler_path, silent, ["--strip-unused-srgs"])
    if ok:
        if not silent: print(Foreground.CYAN + Style.BRIGHT + "testUnusedSrgStripping: Verifying srgs were stripped..." + Style.RESET_ALL)
        predicates = []
        for srgName in alive_srgs:
            symbol_expr = "Symbol '{}'".format(srgName)
            predicates.append(lambda symbol_expr=symbol_expr: symbols[symbol_expr]['kind'] == 'ShaderResourceGroup')
        for srgName in dead_srgs:
            symbol_expr = "Symbol '{}'".format(srgName)
            predicates.append(lambda symbol_expr=symbol_expr: symbol_expr not in symbols)
        ok = compiler.verify_all_predicates(predicates, symbols, silent)
        if ok and not silent:
            print(Style.BRIGHT + "OK! " + str(len(predicates)) + " testUnusedSrgStripping: Verified srgs were stripped." + Style.RESET_ALL)
    return ok


result = 0  # to define for subtests
result_failed = 0


def do_tests(compiler, silent):
    global result
    global result_failed

    # Working directory should have been set to this script's directory by the calling parent
    # You can get it once do_tests() is called, but not during initialization of the module,
    #  because at that time it will still be set to the working directory of the calling script
    work_dir = os.getcwd()

    if not silent: print("testing for removal of two SRGs...")
    if test_unused_srg_stripping(os.path.join(work_dir, "srg-strip-unused-SRG2-SRG3.azsl"), compiler, silent,
                                 alive_srgs=["/SRG1"], dead_srgs=["/SRG2", "/SRG3"]):
        result += 1
    else:
        result_failed += 1

    if not silent: print("testing for removal of one SRG...")
    if test_unused_srg_stripping(os.path.join(work_dir, "srg-strip-unused-SRG3.azsl"), compiler, silent,
                                 alive_srgs=["/SRG1", "/SRG2"], dead_srgs=["/SRG3"]):
        result += 1
    else:
        result_failed += 1

    if not silent: print("testing for survival of all SRGs...")
    if test_unused_srg_stripping(os.path.join(work_dir, "srg-strip-unused-none.azsl"), compiler, silent,
                                 alive_srgs=["/SRG1", "/SRG2", "/SRG3"], dead_srgs=[]):
        result += 1
    else:
        result_failed += 1

    if not silent: print("testing for removal of unused partial SRG...")
    if test_unused_srg_stripping(os.path.join(work_dir, "srg-strip-unused-partial.azsl"), compiler, silent,
                                 alive_srgs=["/MainSRG"], dead_srgs=["/PartialSRG1", "/CompleteSRG2"]):
        result += 1
    else:
        result_failed += 1

    if not silent: print("testing for survival of all SRGs because one of them has the fallback key...")
    if test_unused_srg_stripping(os.path.join(work_dir, "srg-strip-unused-none-fallback.azsl"), compiler, silent,
                                 alive_srgs=["/SRG1", "/SRG2", "/SRG3"], dead_srgs=[]):
        result += 1
    else:
        result_failed += 1


if __name__ == "__main__":
    assert "please call from runner.py"
