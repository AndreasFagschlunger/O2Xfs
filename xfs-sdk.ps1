# Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions
# are met:
#
#   - Redistributions of source code must retain the above copyright
#     notice, this list of conditions and the following disclaimer.
#
#   - Redistributions in binary form must reproduce the above copyright
#     notice, this list of conditions and the following disclaimer in the
#     documentation and/or other materials provided with the distribution.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
# IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
# THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
# PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
# CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
# EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
# PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
# PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
# LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
# NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
# SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

Param(
  [string]$sevenZipPath = '',
  [string]$sdkPath = 'C:\XFS SDKs'
)

if($sevenZipPath.length -gt 0 -and -not $sevenZipPath.EndsWith('\')) {
  $sevenZipPath = $sevenZipPath + '\'
}

if($sdkPath.EndsWith('\')) {
  $sdkPath = $sdkPath.Substring(0, $sdkPath.length - 1)
}

$zipExe = $sevenZipPath+ '7z.exe'

[Net.ServicePointManager]::SecurityProtocol = 'Tls12'

New-Item -ItemType 'directory' -Force -Path $sdkPath
Invoke-WebRequest 'https://repo.fagschlunger.co.at/artifactory/install/eu/cencenelec/XFS/sdk303.zip' -OutFile "$sdkPath\sdk303.zip"
Invoke-WebRequest 'https://repo.fagschlunger.co.at/artifactory/install/eu/cencenelec/XFS/XFS310SDKInstall.zip' -OutFile "$sdkPath\XFS310SDKInstall.zip"
Invoke-WebRequest 'https://repo.fagschlunger.co.at/artifactory/install/eu/cencenelec/XFS/SDK320.zip' -OutFile "$sdkPath\SDK320.zip"
Invoke-WebRequest 'https://repo.fagschlunger.co.at/artifactory/install/eu/cencenelec/XFS/SDK330.zip' -OutFile "$sdkPath\SDK330.zip"

& "$zipExe" e -o"$sdkPath" -r "$sdkPath\sdk303.zip" Install.EXE
& "$zipExe" e -o"$sdkPath" -r "$sdkPath\Install.EXE" XFS_Manager_SDK.msi Data1.cab
& msiexec /a "$sdkPath\XFS_Manager_SDK.msi" /qb TARGETDIR='"'$sdkPath'\SDK303"'

& "$zipExe" e -o"$sdkPath" -r "$sdkPath\XFS310SDKInstall.zip" XFS310SDKInstall.exe
& "$sdkPath\XFS310SDKInstall.exe" /a /s /v'/qn TARGETDIR=\"'$sdkPath'\SDK310\"'

& "$zipExe" e -o"$sdkPath" -r "$sdkPath\SDK320.zip"
& msiexec /a "$sdkPath\SDK320.msi" /qn TARGETDIR='"'$sdkPath'\SDK320"'

& "$zipExe" e -o"$sdkPath" -r "$sdkPath\SDK330.zip"
& msiexec /a "$sdkPath\SDK330.msi" /qn TARGETDIR='"'$sdkPath'\SDK330"'