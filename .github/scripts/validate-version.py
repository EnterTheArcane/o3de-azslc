#
# Copyright (c) Contributors to the Open 3D Engine Project.
# For complete copyright and license terms please see the LICENSE at the root of this distribution.
#
# SPDX-License-Identifier: Apache-2.0 OR MIT
#
#

"""
Validate that the git tag version matches the project version from CMake.

Reads the tag from the GITHUB_REF_NAME environment variable, or accepts
it as an optional CLI argument for local testing.
"""

import os
import re
import subprocess
import sys
import tempfile
from pathlib import Path


def get_repo_root() -> Path:
    return Path(__file__).resolve().parent.parent.parent


def extract_cmake_version(repo_root: Path) -> str:
    with tempfile.TemporaryDirectory(prefix="azslc-version-") as tmpdir:
        result = subprocess.run(
            [
                "cmake",
                "-S", str(repo_root),
                "-B", tmpdir,
                "-DAZSLC_VERSION_ONLY=ON",
            ],
            capture_output=True,
            text=True,
        )
        if result.returncode != 0:
            print(f"::error::CMake configure failed:\n{result.stderr}", file=sys.stderr)
            raise SystemExit(1)

        cache_path = Path(tmpdir) / "CMakeCache.txt"
        cache_content = cache_path.read_text()

        match = re.search(
            r"^CMAKE_PROJECT_VERSION:STATIC=(.+)$", cache_content, re.MULTILINE
        )
        if not match:
            print(
                "::error::CMAKE_PROJECT_VERSION not found in CMake cache.",
                file=sys.stderr,
            )
            raise SystemExit(1)

        return match.group(1)


def get_tag_name() -> str:
    if len(sys.argv) >= 2:
        return sys.argv[1]

    tag = os.environ.get("GITHUB_REF_NAME", "")
    if not tag:
        print(
            "::error::No tag provided. Pass as argument or set GITHUB_REF_NAME.",
            file=sys.stderr,
        )
        raise SystemExit(1)

    return tag


def set_github_output(key: str, value: str) -> None:
    output_file = os.environ.get("GITHUB_OUTPUT")
    if output_file:
        with open(output_file, "a") as f:
            f.write(f"{key}={value}\n")


def main() -> int:
    repo_root = get_repo_root()
    cmake_version = extract_cmake_version(repo_root)
    tag_name = get_tag_name()

    # Strip leading 'v' from tag (e.g., "v1.9.0" -> "1.9.0")
    tag_version = tag_name.removeprefix("v")

    set_github_output("version", cmake_version)

    if tag_version != cmake_version:
        print(
            f"::error::Tag version ({tag_version}) does not match "
            f"CMakeLists.txt version ({cmake_version}).",
            file=sys.stderr,
        )
        print(
            f"::error::Update the version in CMakeLists.txt to match the tag, "
            f"or push the correct tag.",
            file=sys.stderr,
        )
        return 1

    print(f"Tag version ({tag_version}) matches CMake version ({cmake_version}).")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
