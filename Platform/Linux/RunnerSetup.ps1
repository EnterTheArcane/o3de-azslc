#!/usr/bin/env pwsh
$ErrorActionPreference = "Stop"
$PSNativeCommandUseErrorActionPreference = $true

if (-not $IsLinux)
{
    throw "This script only supports Linux hosts"
}

Write-Host "Add target architectures"
dpkg --add-architecture amd64
dpkg --add-architecture arm64

Write-Host "Add arm64 apt sources"
@"
Types: deb
URIs: http://ports.ubuntu.com/ubuntu-ports/
Suites: noble noble-updates noble-backports noble-security
Components: main restricted universe multiverse
Architectures: arm64
Signed-By: /usr/share/keyrings/ubuntu-archive-keyring.gpg
"@ | Out-File -FilePath /etc/apt/sources.list.d/ubuntu-arm64.sources -Encoding utf8

Write-Host "Updating package lists"
apt-get update

Write-Host "Installing cross-compilation dependencies"
apt-get install -y --no-install-recommends `
    gcc-aarch64-linux-gnu g++-aarch64-linux-gnu `
    gcc-x86-64-linux-gnu g++-x86-64-linux-gnu `
    libc6:amd64 libc6:arm64

Write-Host "Installing emulation dependencies"
apt-get install -y --no-install-recommends `
    binfmt-support qemu-user-static
