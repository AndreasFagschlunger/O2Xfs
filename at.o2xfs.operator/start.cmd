@ECHO OFF
CLS
SET workingDirectory=%~dp0
SET workingDirectory=%workingDirectory:~0,-1%
IF NOT EXIST %workingDirectory%\\%COMPUTERNAME% MD %workingDirectory%\\%COMPUTERNAME%
java -Dlog4j.configuration=file:/%workingDirectory%\\config\\log4j.xml -jar %workingDirectory%\\lib\\at.o2xfs.operator-${version}.jar > %workingDirectory%\\%COMPUTERNAME%\\out.log 2> %workingDirectory%\\%COMPUTERNAME%\\err.log