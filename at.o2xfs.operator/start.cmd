@ECHO OFF
CLS
SET workingDirectory=%~dp0
SET workingDirectory=%workingDirectory:~0,-1%
IF NOT EXIST %workingDirectory%\%COMPUTERNAME% MD %workingDirectory%\%COMPUTERNAME%
"C:\Program Files (x86)\Java\jre7\bin\java.exe" -Dlog4j.configuration=file:/%workingDirectory%\config\log4j.xml -Djava.library.path=%workingDirectory%\bin -jar %workingDirectory%\lib\at.o2xfs.operator.jar > %workingDirectory%\%COMPUTERNAME%\out.log 2> %workingDirectory%\%COMPUTERNAME%\err.log
MOVE /Y *.log* %workingDirectory%\%COMPUTERNAME%