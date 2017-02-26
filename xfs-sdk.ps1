$path = 'C:\XFS SDKs'
$zipExe = '7z.exe'

if($args.count -gt 0) {
  $path = $args[0]
}
if($args.count -gt 1) {
  $zipExe = $args[1] + '\' + $zipExe
}

New-Item -ItemType 'directory' -Force -Path $path
Invoke-WebRequest 'https://repo.fagschlunger.co.at/install/eu/cencenelec/XFS/sdk303.zip' -OutFile "$path\sdk303.zip"
Invoke-WebRequest 'https://repo.fagschlunger.co.at/install/eu/cencenelec/XFS/XFS310SDKInstall.zip' -OutFile "$path\XFS310SDKInstall.zip"
Invoke-WebRequest 'https://repo.fagschlunger.co.at/install/eu/cencenelec/XFS/SDK320.zip' -OutFile "$path\SDK320.zip"
Invoke-WebRequest 'https://repo.fagschlunger.co.at/install/eu/cencenelec/XFS/SDK330.zip' -OutFile "$path\SDK330.zip"

& "$zipExe" e -o"$path" -r "$path\sdk303.zip" Install.EXE
& "$zipExe" e -o"$path" -r "$path\Install.EXE" XFS_Manager_SDK.msi Data1.cab
& msiexec /a "$path\XFS_Manager_SDK.msi" /qb TARGETDIR='"'$path'\SDK303"'
Remove-Item "$path\Install.EXE"
Remove-Item "$path\XFS_Manager_SDK.msi"
Remove-Item "$path\Data1.cab"

& "$zipExe" e -o"$path" -r '$path\XFS310SDKInstall.zip' XFS310SDKInstall.exe
& "$path\XFS310SDKInstall.exe" /a /s /v'/qn TARGETDIR=\"'$path'\SDK310\"'
Remove-Item "$path\XFS310SDKInstall.exe"

& "$zipExe" e -o"$path" -r "$path\SDK320.zip"
& msiexec /a "$path\SDK320.msi" /qn TARGETDIR='"'$path'\SDK320"'
Remove-Item "$path\SDK320.msi"

& "$zipExe" e -o"$path" -r "$path\SDK330.zip"
& msiexec /a "$path\SDK330.msi" /qn TARGETDIR='"'$path'\SDK330"'
Remove-Item "$path\SDK330.msi"
