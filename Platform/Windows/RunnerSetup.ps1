#!/usr/bin/env pwsh
$ErrorActionPreference = "Stop"
$PSNativeCommandUseErrorActionPreference = $true

if (-not $IsWindows)
{
    throw "This script only supports Windows hosts"
}

Write-Host "No platform-specific setup required"
