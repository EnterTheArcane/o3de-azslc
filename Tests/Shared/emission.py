#!/usr/bin/python
# -*- coding: utf-8 -*-
"""
Copyright (c) Contributors to the Open 3D Engine Project.
For complete copyright and license terms please see the LICENSE at the root of this distribution.

SPDX-License-Identifier: Apache-2.0 OR MIT
"""

import glob
import io
import os
import os.path
import re
from typing import List

from . import compiler
from .colors import *

# Accumulated list of failed test files (for detailed mode reporting).
fail_list: List[str] = []

# Tracks the last regex match position to allow sequential scanning
# without rescanning from the beginning on each call.
_last_end: int = 0


def reset_search_position() -> None:
    """Reset the sequential search position back to the start."""
    global _last_end
    _last_end = 0


def find_pattern(needle: str, haystack: str, negative: bool) -> bool:
    """Search for a whitespace-flexible pattern in the haystack string.

    The needle is split on whitespace and each token is escaped so it is
    matched literally.  Tokens are then joined with ``\\s+`` to allow any
    amount of whitespace between them.

    Searching resumes from the position after the previous match (use
    ``reset_search_position()`` to start over).

    Args:
        needle: The pattern text to search for.
        haystack: The full text to search within.
        negative: If True, the predicate succeeds when the pattern is
            *not* found (negation check).

    Returns:
        True if the predicate is satisfied.
    """
    global _last_end
    words = re.split(r"\s", needle)
    words = [w for w in words if w]
    words = [re.escape(w) for w in words]
    pattern = r"\s+".join(words)
    compiled = re.compile(pattern)
    consumed = haystack[_last_end:]
    match_obj = compiled.search(consumed)
    if match_obj:
        _last_end += match_obj.end()
        return not negative
    return negative


def parse_string_list(text: str) -> List[str]:
    """Parse a bracketed string list from a shader source comment.

    Handles formats like ``['--unique-idx', '--root-sig']`` or plain
    ``--namespace=vk``.
    """
    cleaned = re.sub(r"[\[\],\s]", "", text)
    parts = re.split(r"['\"]", cleaned)
    return [s for s in parts if s != '']


def verify_emission_pattern(
    azsl_file: str,
    patterns_file: str,
    compiler_path: str,
    silent: bool,
    arg_list: List[str]
) -> bool:
    """Compile an AZSL file and verify output against a pattern file.

    The pattern file contains one quoted string per line representing an
    expected token sequence in the emitted HLSL.  Lines starting with
    ``^`` are negation checks (the pattern must NOT appear).

    Returns True if all patterns matched successfully.
    """
    if not os.path.exists(patterns_file):
        print("Pattern file not found: " + patterns_file)
        return False

    extra_args: List[str] = []
    with io.open(patterns_file, "r", encoding="utf-8") as f:
        for line in f:
            if line.find("Cmdargs") >= 0:
                line = line[line.rfind(':') + 1:]
                extra_args = parse_string_list(line)
                if extra_args and not silent:
                    print("Adding extra command line arguments: " + str(extra_args))

    shader_code, ok = compiler.build_and_get(
        azsl_file, compiler_path, silent, arg_list + extra_args
    )
    if ok:
        if not silent:
            print(
                Style.BRIGHT + Foreground.CYAN +
                "Now to check emission patterns for " + patterns_file +
                Style.RESET_ALL
            )
        # Normalize the shader code by inserting spaces around identifiers
        # stuck to other things, e.g. 'func()' becomes 'func ( )'.
        all_idents = re.split(
            r"([a-zA-Z_]+[a-zA-Z_0-9]*)|(\.)|(,)|(::)|(;)|(\()|(\))|(<)|(>)|( )",
            shader_code.decode('utf-8')
        )
        all_idents = [
            s for s in all_idents
            if s is not None and s != "" and s != " "
        ]
        normalized_code = " ".join(all_idents)

        predicates = []
        with io.open(patterns_file, encoding="utf-8") as f:
            i = 0
            for line in f:
                negation = line.startswith('^')
                if negation:
                    line = line[1:]
                if line.startswith('"'):
                    line = line.strip().strip('"')
                    if not silent:
                        print(
                            Foreground.CYAN + "Verify (" + str(i) + ")" +
                            (" not" if negation else "") + " : " +
                            line + Style.RESET_ALL
                        )
                    predicates.append(
                        lambda line=line, negation=negation:
                        find_pattern(line, normalized_code, negation)
                    )
                    i += 1

        reset_search_position()
        ok = compiler.verify_all_predicates(predicates, normalized_code, silent)
    return ok


def verify_emission_patterns(
    thefile: str,
    compiler_path: str,
    silent: bool,
    arg_list: List[str]
) -> int:
    """Verify all pattern files associated with the given AZSL source.

    Pattern files are discovered by matching ``<basename>*.txt`` or
    ``<basename>-[0-9].txt`` in the same directory.

    Returns the number of successfully verified pattern files, or 0 on
    any failure.
    """
    global fail_list
    local_fail_list: List[str] = []
    result = 0
    base = os.path.basename(thefile)
    file_prefix = os.path.dirname(thefile) + '/' + os.path.splitext(base)[0]

    for file in (
        glob.glob('%s*[0-9].txt' % file_prefix) or
        glob.glob('%s.txt' % file_prefix)
    ):
        if not verify_emission_pattern(
            thefile, file, compiler_path, silent, arg_list
        ):
            local_fail_list.append(file)
        else:
            result += 1

    if local_fail_list:
        fail_list.extend(local_fail_list)
        return 0
    return result


def print_failed_test_list(silent: bool) -> None:
    """Print the accumulated list of failed test files and reset it."""
    global fail_list
    if not silent and fail_list:
        print(
            Style.BRIGHT + Foreground.RED + "failed files: " +
            Foreground.WHITE + str(fail_list) + Style.RESET_ALL
        )
    # Reset so the list doesn't carry over between platform runs.
    fail_list = []


def compile_and_expect_error(
    thefile: str,
    compiler_path: str,
    silent: bool,
    arg_list: List[str]
) -> int:
    """Compile a file expecting failure and verify the error code.

    The expected error code is read from a ``#EC <number>`` comment in
    the source file.

    Returns 1 on success (error code matched) or 0 on failure.
    """
    global fail_list

    options = [thefile] + list(arg_list)
    out, err, code = compiler.launch_compiler(compiler_path, options, silent)

    if code == 0:
        if not silent:
            print(
                Foreground.RED + Style.BRIGHT +
                f"FAIL. Expected {thefile} to report compilation errors" +
                Style.RESET_ALL
            )
        fail_list.append(thefile)
        return 0

    # Read the expected error code from the source file.
    with io.open(thefile, 'r', encoding="utf-8") as f:
        azsl_code = f.read()

    expected_ec = compiler.find_token_to_int(azsl_code, r"#EC\s\d*")
    actual_ec = compiler.find_token_to_int(
        err.decode('utf-8'), r"error\s#\d*:"
    )

    if actual_ec == expected_ec:
        return 1

    if not silent:
        print(
            Foreground.RED + Style.BRIGHT +
            f"FAIL. Expected error code {expected_ec} from {thefile}, "
            f"instead got error code {actual_ec}" +
            Style.RESET_ALL
        )
    fail_list.append(thefile)
    return 0
