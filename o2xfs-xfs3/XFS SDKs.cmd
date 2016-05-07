@ECHO OFF
SET workingDirectory=%~dp0
SET workingDirectory=%workingDirectory:~0,-1%

IF NOT EXIST "%workingDirectory%\XFS_Manager_SDK.msi" (
  IF EXIST "%workingDirectory%\Install.EXE" (
    ECHO Install.EXE from XFS SDK 3.00 found.
    ECHO Either extract the contents of Install.EXE with a tool like WinRAR or proceeed and CANCEL XFS_Manager_SDK installation.
    PAUSE
    IF NOT EXIST "%workingDirectory%\XFS_Manager_SDK.msi" (
      "%workingDirectory%\Install.EXE" /s /f"%workingDirectory%"
    )
  )
)

IF NOT EXIST "%workingDirectory%\XFS_Manager_SDK.msi" (
  ECHO XFS SDK 3.00 ^(XFS_Manager_SDK.msi^) not found.
) ELSE (
  ECHO Extracting XFS SDK 3.00 ...
  msiexec /a "%workingDirectory%\XFS_Manager_SDK.msi" /qb TARGETDIR="%workingDirectory%\SDK300"
)

IF NOT EXIST "%workingDirectory%\XFS310SDKInstall.exe" (
  ECHO XFS SDK 3.10 ^(XFS310SDKInstall.exe^) not found.
) ELSE (
  ECHO Extracting XFS SDK 3.10 ...
  "%workingDirectory%\XFS310SDKInstall.exe" /a /s /v"/qn TARGETDIR=\"%workingDirectory%\SDK310\""
)

IF NOT EXIST "%workingDirectory%\SDK320.msi" (
  ECHO XFS SDK 3.20 ^(SDK320.msi^) not found.
) ELSE (
  ECHO Extracting XFS SDK 3.20 ...
  msiexec /a "%workingDirectory%\SDK320.msi" /qb TARGETDIR="%workingDirectory%\SDK320"
)

IF NOT EXIST "%workingDirectory%\SDK320.msi" (
  ECHO XFS SDK 3.20 ^(SDK330.msi^) not found.
) ELSE (
  ECHO Extracting XFS SDK 3.20 ...
  msiexec /a "%workingDirectory%\SDK330.msi" /qb TARGETDIR="%workingDirectory%\SDK330"
)