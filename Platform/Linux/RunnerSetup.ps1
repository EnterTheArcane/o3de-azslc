#!/usr/bin/env pwsh
$ErrorActionPreference = "Stop"
$PSNativeCommandUseErrorActionPreference = $true

if (-not $IsLinux)
{
    throw "This script only supports Linux hosts"
}

Write-Host "Configure apt sources"
@"
Types: deb
URIs: http://archive.ubuntu.com/ubuntu/
Suites: noble
Components: main restricted universe
Architectures: amd64

Types: deb
URIs: http://security.ubuntu.com/ubuntu/
Suites: noble-security
Components: main restricted universe
Architectures: amd64

Types: deb
URIs: http://archive.ubuntu.com/ubuntu/
Suites: noble-updates
Components: main restricted universe
Architectures: amd64

Types: deb
URIs: http://azure.ports.ubuntu.com/ubuntu-ports/
Suites: noble
Components: main restricted multiverse universe
Architectures: arm64

Types: deb
URIs: http://azure.ports.ubuntu.com/ubuntu-ports/
Suites: noble-updates
Components: main restricted multiverse universe
Architectures: arm64
"@ | sudo tee /etc/apt/sources.list > $null

Write-Host "Updating package lists"
sudo apt-get update

Write-Host "Installing cross-compilation dependencies"
sudo apt-get install -y --no-install-recommends `
    gcc-aarch64-linux-gnu g++-aarch64-linux-gnu `
    gcc-x86-64-linux-gnu g++-x86-64-linux-gnu `
    libc6-dev-amd64-cross libgcc-14-dev-amd64-cross libstdc++-14-dev-amd64-cross `
    libc6-dev-arm64-cross libgcc-14-dev-arm64-cross libstdc++-14-dev-arm64-cross

Write-Host "Installing emulation dependencies"
apt-get install -y --no-install-recommends `
    binfmt-support qemu-user-static `
    libc6:amd64 libstdc++6:amd64 libgcc-s1:amd64 `
    libc6:arm64 libstdc++6:arm64 libgcc-s1:arm64
