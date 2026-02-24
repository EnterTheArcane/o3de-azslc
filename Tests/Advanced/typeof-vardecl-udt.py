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


def test(thefile, compiler_path, silent):
    '''return number of successes'''
    symbols, ok = compiler.build_and_get_symbols(thefile, compiler_path, silent)
    if ok:
        try:
            ok = symbols["Symbol '/g_s'"]['kind'] == 'Variable'
            ok = ok and symbols["Symbol '/g_s'"]['type']['core']['name'] == '/S'

            if not silent:
                if not ok:
                    print(Foreground.RED + "ERR: g_s type could not be validated" + Style.RESET_ALL)
                else:
                    print(Style.BRIGHT + "OK! " + Style.RESET_ALL)
        except Exception as e:
            print(Foreground.RED + "Err: Parsed --dumpsym may lack some expected keys" + Style.RESET_ALL, e)
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

    if test(os.path.join(work_dir, "../Semantic/combined-vardecl-udt.azsl"), compiler, silent):
        result += 1
    else:
        result_failed += 1


if __name__ == "__main__":
    assert "please call from runner.py"
