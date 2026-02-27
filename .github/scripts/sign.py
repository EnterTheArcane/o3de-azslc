#!/usr/bin/env python3
#
# Copyright (c) Contributors to the Open 3D Engine Project.
# For complete copyright and license terms please see the LICENSE at the root of this distribution.
#
# SPDX-License-Identifier: Apache-2.0 OR MIT
#
#

"""
Imports a code signing certificate and signs the specified files.

Usage:
    python sign.py <file> [<file> ...]

Apple:
    Decodes APPLE_CERTIFICATE (base64 .p12) into a temporary keychain,
    then signs each file with `codesign` using APPLE_SIGNING_IDENTITY.

Linux:
    No-op (code signing is not supported).

Windows:
    Decodes WINDOWS_CERTIFICATE (base64 .pfx) into the user certificate
    store via certutil, then signs each file with `signtool` using
    WINDOWS_CERTIFICATE_THUMBPRINT.

Environment variables:
    APPLE_CERTIFICATE                Base64-encoded .p12 certificate
    APPLE_CERTIFICATE_PASSWORD       Password for the .p12 file
    APPLE_SIGNING_IDENTITY           Code signing identity string

    WINDOWS_CERTIFICATE              Base64-encoded .pfx certificate
    WINDOWS_CERTIFICATE_PASSWORD     Password for the .pfx file
    WINDOWS_CERTIFICATE_THUMBPRINT   SHA-1 certificate thumbprint
"""

import argparse
import base64
import os
import platform
import secrets
import subprocess
import sys
import tempfile


def run(*args):
    print(f"> {' '.join(args)}")
    subprocess.run(args, check=True)


def import_certificate_apple():
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
        run("security", "import", cert_path, "-P", password, "-A", "-f", "pkcs12", "-k", keychain_path)
        run("security", "set-key-partition-list", "-S", "apple-tool:,apple:", "-k", keychain_password, keychain_path)
        run("security", "list-keychains", "-d", "user", "-s", keychain_path, "login.keychain-db")

        print(f"Certificate imported into keychain: {keychain_path}")
    finally:
        if os.path.exists(cert_path):
            os.remove(cert_path)


def sign_apple(files):
    identity = os.environ.get("APPLE_SIGNING_IDENTITY", "-")

    sign_args = ["--force", "--sign", identity]

    if identity != "-":
        sign_args.append("--timestamp")

    sign_args.append("--options")
    sign_args.append("runtime")

    for path in files:
        print(f"Signing {path}")
        run("codesign", *sign_args, path)


def import_certificate_windows():
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


def sign_windows(files):
    thumbprint = os.environ.get("WINDOWS_CERTIFICATE_THUMBPRINT", "")
    if not thumbprint:
        print("WINDOWS_CERTIFICATE_THUMBPRINT is not set; cannot sign.", file=sys.stderr)
        sys.exit(1)

    sign_args = [
        "sign",
        "/sha1", thumbprint,
        "/fd", "SHA256",
        "/tr", "https://timestamp.digicert.com",
        "/td", "SHA256",
    ]

    for path in files:
        print(f"Signing {path}")
        run("signtool", *sign_args, path)


def main():
    parser = argparse.ArgumentParser(description="Import certificates and sign files.")
    parser.add_argument("files", nargs="+", help="Files to sign")
    args = parser.parse_args()

    system = platform.system()

    if system == "Darwin":
        import_certificate_apple()
        sign_apple(args.files)
    elif system == "Windows":
        import_certificate_windows()
        sign_windows(args.files)
    else:
        print(f"Code signing is not supported on {system}; skipping.")


if __name__ == "__main__":
    main()
