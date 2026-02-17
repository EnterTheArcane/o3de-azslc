#!/usr/bin/env python3
#
# Copyright (c) Contributors to the Open 3D Engine Project.
# For complete copyright and license terms please see the LICENSE at the root of this distribution.
#
# SPDX-License-Identifier: Apache-2.0 OR MIT
#
#

"""
Imports a code signing certificate into the system keystore.

Darwin: Decodes APPLE_CERTIFICATE (base64 .p12) and imports it into a temporary keychain so that `codesign` can find it.

Linux: No-op (code signing is not supported).

Windows: Decodes WINDOWS_CERTIFICATE (base64 .pfx) and imports it into the current user's certificate store.

Environment variables:
    RUNNER_TEMP: Temp directory (provided by GitHub Actions)

    APPLE_CERTIFICATE: Base64-encoded .p12 certificate (macOS)
    APPLE_CERTIFICATE_PASSWORD: Password for the .p12 file (macOS)

    WINDOWS_CERTIFICATE: Base64-encoded .pfx certificate (Windows)
    WINDOWS_CERTIFICATE_PASSWORD: Password for the .pfx file (Windows)
"""

import base64
import os
import platform
import secrets
import subprocess
import tempfile


def run(*args):
    subprocess.run(args, check=True)


def setup_darwin():
    cert_b64 = os.environ.get("APPLE_CERTIFICATE")
    if not cert_b64:
        print("APPLE_CERTIFICATE is not set; skipping certificate import.")
        return

    password = os.environ.get("APPLE_CERTIFICATE_PASSWORD", "")
    tmp = os.environ.get("RUNNER_TEMP", tempfile.gettempdir())
    keychain_path = os.path.join(tmp, "signing.keychain-db")
    keychain_password = secrets.token_hex(32)
    cert_path = os.path.join(tmp, "certificate.p12")

    try:
        with open(cert_path, "wb") as f:
            f.write(base64.b64decode(cert_b64))

        run("security", "create-keychain", "-p", keychain_password, keychain_path)
        run("security", "set-keychain-settings", "-lut", "21600", keychain_path)
        run("security", "unlock-keychain", "-p", keychain_password, keychain_path)
        run("security", "import", cert_path, "-P", password, "-A", "-t", "cert", "-f", "pkcs12", "-k", keychain_path)
        run("security", "set-key-partition-list", "-S", "apple-tool:,apple:", "-k", keychain_password, keychain_path)
        run("security", "list-keychains", "-d", "user", "-s", keychain_path, "login.keychain-db")

        print(f"Certificate imported into keychain: {keychain_path}")
    finally:
        if os.path.exists(cert_path):
            os.remove(cert_path)


def setup_windows():
    cert_b64 = os.environ.get("WINDOWS_CERTIFICATE")
    if not cert_b64:
        print("WINDOWS_CERTIFICATE is not set; skipping certificate import.")
        return

    password = os.environ.get("WINDOWS_CERTIFICATE_PASSWORD", "")
    tmp = os.environ.get("RUNNER_TEMP", tempfile.gettempdir())
    cert_path = os.path.join(tmp, "certificate.pfx")

    try:
        with open(cert_path, "wb") as f:
            f.write(base64.b64decode(cert_b64))

        run("certutil", "-f", "-user", "-p", password, "-importPFX", "My", cert_path)

        print("Certificate imported into CurrentUser\\My")
    finally:
        if os.path.exists(cert_path):
            os.remove(cert_path)


def main():
    system = platform.system()

    if system == "Darwin":
        setup_darwin()
    elif system == "Windows":
        setup_windows()
    else:
        print(f"Code signing is not supported on {system}; skipping.")


if __name__ == "__main__":
    main()
