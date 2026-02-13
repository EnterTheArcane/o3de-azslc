#
# Copyright (c) Contributors to the Open 3D Engine Project.
# For complete copyright and license terms please see the LICENSE at the root of this distribution.
#
# SPDX-License-Identifier: Apache-2.0 OR MIT
#
#

"""
Prepare release assets by renaming build artifacts to their release names.

Expects the artifact directory structure produced by actions/download-artifact:
    Artifacts/
        Linux-ARM/azslc
        Linux-X64/azslc
        Mac-ARM/azslc
        Mac-X64/azslc
        Windows-ARM/azslc.exe
        Windows-X64/azslc.exe

Produces renamed binaries in the output directory:
    Release/
        azslc-linux-arm
        azslc-linux-x64
        azslc-mac-arm
        azslc-mac-x64
        azslc-windows-arm.exe
        azslc-windows-x64.exe

Usage:
    python prepare-assets.py [artifacts_dir] [output_dir]
"""

import shutil
import sys
from pathlib import Path


def main() -> int:
    artifacts_dir = Path(sys.argv[1]) if len(sys.argv) >= 2 else Path("Artifacts")
    output_dir = Path(sys.argv[2]) if len(sys.argv) >= 3 else Path("Release")

    if not artifacts_dir.is_dir():
        print(f"::error::Artifacts directory not found: {artifacts_dir}", file=sys.stderr)
        return 1

    output_dir.mkdir(parents=True, exist_ok=True)

    found_any = False
    for platform_dir in sorted(artifacts_dir.iterdir()):
        if not platform_dir.is_dir():
            continue

        platform_arch = platform_dir.name.lower()  # e.g. "windows-x64"

        # Find the binary (azslc.exe on Windows, azslc otherwise)
        binary = platform_dir / "azslc.exe"
        if not binary.exists():
            binary = platform_dir / "azslc"

        if not binary.exists():
            print(f"::error::No binary found in {platform_dir}", file=sys.stderr)
            # List contents for debugging
            for f in platform_dir.iterdir():
                print(f"  {f.name}", file=sys.stderr)
            return 1

        # Build the output name: azslc-linux-x64 or azslc-windows-arm.exe
        extension = binary.suffix  # ".exe" or ""
        output_name = f"azslc-{platform_arch}{extension}"
        output_path = output_dir / output_name

        shutil.copy2(binary, output_path)

        # Ensure the binary is executable
        output_path.chmod(output_path.stat().st_mode | 0o755)

        print(f"Created {output_path}")
        found_any = True

    if not found_any:
        print("::error::No artifact directories found.", file=sys.stderr)
        return 1

    print(f"Release assets in {output_dir}:")
    for f in sorted(output_dir.iterdir()):
        size_mb = f.stat().st_size / (1024 * 1024)
        print(f"  {f.name} ({size_mb:.1f} MB)")

    return 0


if __name__ == "__main__":
    raise SystemExit(main())
