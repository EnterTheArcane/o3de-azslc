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


def verify_input_layouts(thefile, compiler_path, silent):
    j, ok = compiler.build_and_get_json(thefile, compiler_path, silent, ["--ia"])
    if ok:
        predicates = []
        # check all references of func()
        predicates.append(lambda: len(j["inputLayouts"]) == 3)

        predicates.append(lambda: len(j["inputLayouts"][0]["streams"]) == 3)
        predicates.append(lambda: j["inputLayouts"][0]["streams"][0]["name"] == "m_position")
        predicates.append(lambda: j["inputLayouts"][0]["streams"][0]["semanticName"] == "POSITION")
        predicates.append(lambda: j["inputLayouts"][0]["streams"][0]["systemValue"] == False)
        predicates.append(lambda: j["inputLayouts"][0]["streams"][1]["name"] == "m_color")
        predicates.append(lambda: j["inputLayouts"][0]["streams"][1]["dimensions"][0] == 4)
        predicates.append(lambda: j["inputLayouts"][0]["streams"][1]["semanticName"] == "COLOR")
        predicates.append(lambda: j["inputLayouts"][0]["streams"][1]["systemValue"] == False)
        predicates.append(lambda: j["inputLayouts"][0]["streams"][2]["name"] == "vtxIndex")
        predicates.append(lambda: j["inputLayouts"][0]["streams"][2]["semanticName"] == "SV_VertexID")
        predicates.append(lambda: j["inputLayouts"][0]["streams"][2]["systemValue"] == True)

        predicates.append(lambda: len(j["inputLayouts"][1]["streams"]) == 10)
        predicates.append(lambda: j["inputLayouts"][1]["streams"][0]["name"] == "m_position")
        predicates.append(lambda: j["inputLayouts"][1]["streams"][0]["semanticName"] == "POSITION")
        predicates.append(lambda: j["inputLayouts"][1]["streams"][0]["systemValue"] == False)
        predicates.append(lambda: j["inputLayouts"][1]["streams"][9]["name"] == "instId")
        predicates.append(lambda: j["inputLayouts"][1]["streams"][9]["semanticName"] == "SV_InstanceID")
        predicates.append(lambda: j["inputLayouts"][1]["streams"][9]["systemValue"] == True)
        predicates.append(lambda: j["inputLayouts"][1]["streams"][8]["name"] == "vtxIndex")
        predicates.append(lambda: j["inputLayouts"][1]["streams"][8]["semanticName"] == "SV_VertexID")
        predicates.append(lambda: j["inputLayouts"][1]["streams"][8]["systemValue"] == True)

        predicates.append(lambda: len(j["inputLayouts"][2]["streams"]) == 2)
        predicates.append(lambda: j["inputLayouts"][2]["streams"][0]["name"] == "position")
        predicates.append(lambda: j["inputLayouts"][2]["streams"][0]["semanticName"] == "POSITION")
        predicates.append(lambda: j["inputLayouts"][2]["streams"][0]["systemValue"] == False)
        predicates.append(lambda: j["inputLayouts"][2]["streams"][1]["name"] == "color")
        predicates.append(lambda: j["inputLayouts"][2]["streams"][1]["semanticName"] == "COLOR")
        predicates.append(lambda: j["inputLayouts"][2]["streams"][1]["systemValue"] == False)

        if not silent: print(Foreground.CYAN + Style.BRIGHT + "input assembler layouts verification..." + Style.RESET_ALL)
        ok = compiler.verify_all_predicates(predicates, j)
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

    if verify_input_layouts(os.path.join(work_dir, "input-assembler.azsl"), compiler, silent):
        result += 1
    else:
        result_failed += 1


if __name__ == "__main__":
    assert "please call from runner.py"
