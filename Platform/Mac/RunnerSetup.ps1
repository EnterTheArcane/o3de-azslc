#!/usr/bin/env pwsh
$ErrorActionPreference = "Stop"
$PSNativeCommandUseErrorActionPreference = $true

if (-not $IsMacOS)
{
    throw "This script only supports Mac hosts"
}

Write-Host "No platform-specific setup required"
