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
Invoke-WebRequest 'https://repo.fagschlunger.co.at/install/eu/cencenelec/XFS/sdk303.zip' -OutFile "$sdkPath\sdk303.zip"
Invoke-WebRequest 'https://repo.fagschlunger.co.at/install/eu/cencenelec/XFS/XFS310SDKInstall.zip' -OutFile "$sdkPath\XFS310SDKInstall.zip"
Invoke-WebRequest 'https://repo.fagschlunger.co.at/install/eu/cencenelec/XFS/SDK320.zip' -OutFile "$sdkPath\SDK320.zip"
Invoke-WebRequest 'https://repo.fagschlunger.co.at/install/eu/cencenelec/XFS/SDK330.zip' -OutFile "$sdkPath\SDK330.zip"

& "$zipExe" e -o"$sdkPath" -r "$sdkPath\sdk303.zip" Install.EXE
& "$zipExe" e -o"$sdkPath" -r "$sdkPath\Install.EXE" XFS_Manager_SDK.msi Data1.cab
& msiexec /a "$sdkPath\XFS_Manager_SDK.msi" /qb TARGETDIR='"'$sdkPath'\SDK303"'

& "$zipExe" e -o"$sdkPath" -r "$sdkPath\XFS310SDKInstall.zip" XFS310SDKInstall.exe
& "$sdkPath\XFS310SDKInstall.exe" /a /s /v'/qn TARGETDIR=\"'$sdkPath'\SDK310\"'

& "$zipExe" e -o"$sdkPath" -r "$sdkPath\SDK320.zip"
& msiexec /a "$sdkPath\SDK320.msi" /qn TARGETDIR='"'$sdkPath'\SDK320"'

& "$zipExe" e -o"$sdkPath" -r "$sdkPath\SDK330.zip"
& msiexec /a "$sdkPath\SDK330.msi" /qn TARGETDIR='"'$sdkPath'\SDK330"'
