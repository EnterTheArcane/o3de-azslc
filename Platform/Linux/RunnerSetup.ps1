#!/usr/bin/env pwsh
$ErrorActionPreference = "Stop"

Write-Host "Updating package lists"
sudo apt-get update

Write-Host "Installing cross-compilation dependencies"
sudo apt-get install -y --no-install-recommends `
    gcc-aarch64-linux-gnu g++-aarch64-linux-gnu `
    gcc-x86-64-linux-gnu g++-x86-64-linux-gnu

Write-Host "Installing emulation dependencies"
sudo apt-get install -y --no-install-recommends `
    binfmt-support qemu-user-static
