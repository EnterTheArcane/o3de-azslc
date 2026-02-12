#!/usr/bin/env pwsh
$ErrorActionPreference = "Stop"
$PSNativeCommandUseErrorActionPreference = $true

Write-Host "Add target architectures"
sudo dpkg --add-architecture amd64
sudo dpkg --add-architecture arm64

Write-Host "Updating package lists"
sudo apt-get update --error-on=any

Write-Host "Installing cross-compilation dependencies"
sudo apt-get install -y --no-install-recommends `
    gcc-aarch64-linux-gnu g++-aarch64-linux-gnu `
    gcc-x86-64-linux-gnu g++-x86-64-linux-gnu `
    libc6:amd64 libc6:arm64

Write-Host "Installing emulation dependencies"
sudo apt-get install -y --no-install-recommends `
    binfmt-support qemu-user-static
