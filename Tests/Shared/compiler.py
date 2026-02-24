#!/usr/bin/python
# -*- coding: utf-8 -*-
"""
Copyright (c) Contributors to the Open 3D Engine Project.
For complete copyright and license terms please see the LICENSE at the root of this distribution.

SPDX-License-Identifier: Apache-2.0 OR MIT
"""

import inspect
import pprint
import re
import subprocess
import sys
from typing import Any, Dict, List, Optional, Tuple


def find_token_to_int(output: str, expression: str) -> int:
    """Search for a regex pattern in output and extract the numeric value.

    Returns the integer value found, -1 if the pattern is not found,
    or -2 if the matched text contains no valid integer.
    """
    match = re.search(expression, output)
    if not match:
        return -1
    digits = re.sub(r"\D", "", match.group())
    return int(digits) if digits.isdigit() else -2


def first_of(haystack: bytes, *needles: str) -> int:
    """Return the index of the earliest occurrence of any needle in haystack.

    Returns -1 if none of the needles are found.
    """
    decoded = haystack.decode('utf-8')
    positions = [decoded.find(n) for n in needles]
    valid = [p for p in positions if p != -1]
    return min(valid) if valid else -1


def strip_quotes(text: str) -> str:
    """Strip leading/trailing whitespace and surrounding quotes from text."""
    return text.strip().strip('"').strip("'")


def verify_partition_predicate(operands: List[str]) -> bool:
    """Verify a partitioned predicate expression (==, !=)."""
    if operands[1] == '==':
        return operands[0] == operands[2]
    if operands[1] == '!=':
        return operands[0] != operands[2]
    return False


def execute_predicate_checks(message: bytes) -> Tuple[bool, int]:
    """Parse and verify all '@check predicate' annotations in compiler output.

    Returns a tuple of (all_passed, num_checked).
    """
    num_checked = 0
    look_for = "@check predicate"
    for m in re.finditer(look_for, message.decode('utf-8')):
        num_checked += 1
        start = m.start() + len(look_for)
        leftover = message[start:]
        stop = first_of(leftover, '\\n', '\n', '"@')
        whole_expr = leftover[:stop]
        operands = whole_expr.decode('utf-8').partition("==")
        operands = [strip_quotes(s) for s in operands]
        if not verify_partition_predicate(operands):
            print("TEST error:", operands)
            return (False, num_checked)
    return (True, num_checked)


def launch_compiler(
    compiler_path: str,
    options: List[str],
    silent: bool
) -> Tuple[bytes, bytes, int]:
    """Run the AZSLC compiler with the given options.

    Returns a tuple of (stdout, stderr, return_code).
    """
    arg_list = [compiler_path] + list(options)
    print("    Running: ", ' '.join(arg_list))
    process = subprocess.Popen(
        arg_list, stdout=subprocess.PIPE, stderr=subprocess.PIPE
    )
    out, err = process.communicate()
    if not silent:
        sys.stdout.write(out.decode('utf-8'))
        sys.stderr.write(err.decode('utf-8'))
    return (out, err, process.returncode)


def parse_yaml(text: bytes) -> Optional[Dict[str, Any]]:
    """Parse YAML text (designed for --dumpsym output).

    Returns the parsed dictionary or None on failure.
    """
    try:
        import yaml
    except ImportError:
        print("no yaml module. execute `pip install pyyaml`")
        return None
    try:
        return yaml.load(text, Loader=yaml.FullLoader)
    except yaml.YAMLError as e:
        print("Parsing YAML string failed")
        if hasattr(e, "reason"):
            print("Reason:", e.reason)
            print("At position: {0} (line {1}) with encoding {2}".format(
                e.position,
                len(text[0:e.position].splitlines()),
                e.encoding
            ))
            print("Invalid char code:", e.character)
            print("culprit context:", text[e.position - 8: e.position + 8])
        else:
            raise
        raise
    except AttributeError as ae:
        if "has no attribute 'FullLoader'" in str(ae):
            return yaml.load(text)
        else:
            raise


def build_and_get_symbols(
    thefile: str,
    compiler_path: str,
    silent: bool,
    extra_args: Optional[List[str]] = None
) -> Tuple[Optional[Dict[str, Any]], bool]:
    """Compile with --dumpsym and return the parsed symbol table.

    Returns a tuple of (symbols_dict, success_bool).
    """
    from . import colors
    if extra_args is None:
        extra_args = []
    stdout, stderr, code = launch_compiler(
        compiler_path, [thefile, "--dumpsym"] + extra_args, silent
    )
    if code != 0:
        if not silent:
            print(
                colors.Foreground.RED + colors.Style.BRIGHT +
                "compilation failed " + colors.Style.NORMAL +
                thefile + colors.Style.RESET_ALL
            )
        return (None, False)
    symbols = parse_yaml(stdout)
    if symbols is None:
        print(
            colors.Foreground.RED +
            "Parsing result of --dumpsym failed" +
            colors.Style.RESET_ALL
        )
        return (None, False)
    return (symbols, True)


def build_and_get_json(
    thefile: str,
    compiler_path: str,
    silent: bool,
    extra_args: List[str]
) -> Tuple[Optional[Dict[str, Any]], bool]:
    """Compile with extra arguments and return the parsed JSON output.

    Returns a tuple of (json_dict, success_bool).
    """
    from . import colors
    import json

    stdout, stderr, code = launch_compiler(
        compiler_path, [thefile] + extra_args, silent
    )
    if code != 0:
        if not silent:
            print(
                colors.Foreground.RED + colors.Style.BRIGHT +
                "compilation failed " + colors.Style.NORMAL +
                str(extra_args) + " " + thefile + colors.Style.RESET_ALL
            )
        return (None, False)
    parsed = json.loads(stdout)
    if parsed is None:
        print(
            colors.Foreground.RED +
            "Parsing result of " + str(extra_args) + " failed" +
            colors.Style.RESET_ALL
        )
        return (None, False)
    return (parsed, True)


def build_and_get(
    thefile: str,
    compiler_path: str,
    silent: bool,
    extra_args: List[str]
) -> Tuple[Optional[bytes], bool]:
    """Compile and return raw stdout on success.

    Returns a tuple of (stdout_bytes, success_bool).
    """
    from . import colors
    # Force silent to avoid polluting caller's output
    stdout, stderr, code = launch_compiler(
        compiler_path, [thefile] + extra_args, True
    )
    if code != 0:
        if not silent:
            print(
                colors.Foreground.RED +
                "compilation of " + thefile + " failed" +
                colors.Style.RESET_ALL
            )
        return (None, False)
    return (stdout, True)


def build_and_get_error(
    thefile: str,
    compiler_path: str,
    silent: bool,
    extra_args: List[str]
) -> Tuple[Optional[bytes], bool]:
    """Compile expecting failure and return stderr on failure.

    Returns a tuple of (stderr_bytes, did_fail_bool).
    """
    from . import colors
    # Force silent to avoid polluting caller's output
    stdout, stderr, code = launch_compiler(
        compiler_path, [thefile] + extra_args, True
    )
    if code == 0:
        if not silent:
            print(
                colors.Foreground.RED +
                "compilation of " + thefile +
                " succeeded. Was expecting failure." +
                colors.Style.RESET_ALL
            )
        return (None, False)
    return (stderr, True)


def dump_keywords(
    compiler_path: str
) -> Tuple[Optional[Dict[str, Any]], bool]:
    """Run --listpredefined and return the parsed keyword table.

    Returns a tuple of (keywords_dict, success_bool).
    """
    from . import colors
    stdout, stderr, code = launch_compiler(
        compiler_path, ["--listpredefined"], False
    )
    if code != 0:
        print(
            colors.Foreground.RED +
            "compilation failed" +
            colors.Style.RESET_ALL
        )
        return (None, False)
    tokens = parse_yaml(stdout)
    if tokens is None:
        print(
            colors.Foreground.RED +
            "Parsing result of --listpredefined failed" +
            colors.Style.RESET_ALL
        )
        return (None, False)
    return (tokens, True)


def verify_all_predicates(
    predicates: list,
    code: Any,
    silent: bool = True
) -> bool:
    """Execute a list of predicate lambdas and report any failures.

    Each predicate should return True on success. On failure the predicate
    source is printed for debugging. If any predicate fails and silent is
    False, the full parsed data is pretty-printed.

    Returns True if all predicates passed.
    """
    from . import colors
    all_ok = True
    for i, p in enumerate(predicates):
        ok = True
        try:
            ok = p()
        except Exception as e:
            print(
                colors.Foreground.RED + "exception " +
                colors.Style.RESET_ALL + str(e)
            )
            ok = False
        if not ok:
            try:
                src = inspect.getsource(p)
            except Exception:
                src = "<exception>"
            print(
                colors.Foreground.RED + "FAIL (" + str(i) + "):" +
                colors.Style.RESET_ALL + src
            )
        all_ok = all_ok and ok
    if not all_ok and not silent:
        print("dump as parsed:")
        pp = pprint.PrettyPrinter(indent=2, width=160)
        pp.pprint(code)
    return all_ok
