#!/usr/bin/env pwsh
$ErrorActionPreference = "Stop"

Write-Host "Updating package lists"
apt-get update

Write-Host "Installing cross-compilation dependencies"
apt-get install -y --no-install-recommends `
    gcc-aarch64-linux-gnu g++-aarch64-linux-gnu `
    gcc-x86-64-linux-gnu g++-x86-64-linux-gnu

Write-Host "Installing emulation dependencies"
apt-get install -y --no-install-recommends `
    binfmt-support qemu-user-static
