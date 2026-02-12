#!/usr/bin/env pwsh
$ErrorActionPreference = "Stop"
$PSNativeCommandUseErrorActionPreference = $true

if (-not $IsLinux)
{
    throw "This script only supports Linux hosts"
}

Write-Host "Updating package lists"
sudo apt-get update

Write-Host "Installing cross-compilation dependencies"
sudo apt-get install -y --no-install-recommends `
    gcc-aarch64-linux-gnu g++-aarch64-linux-gnu `
    gcc-x86-64-linux-gnu g++-x86-64-linux-gnu `
    libc6-dev-amd64-cross libgcc-14-dev-amd64-cross libstdc++-14-dev-amd64-cross libstdc++6-amd64-cross `
    libc6-dev-arm64-cross libgcc-14-dev-arm64-cross libstdc++-14-dev-arm64-cross libstdc++6-arm64-cross

Write-Host "Installing emulation dependencies"
sudo apt-get install -y --no-install-recommends `
    binfmt-support qemu-user-static
