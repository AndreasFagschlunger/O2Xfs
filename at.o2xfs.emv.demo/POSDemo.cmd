@ECHO OFF
CLS
SET workingDirectory=%~dp0
SET workingDirectory=%workingDirectory:~0,-1%
java -Dlog4j.configuration=file:/%workingDirectory%\log4j.xml -jar %workingDirectory%\lib\at.o2xfs.emv.demo.jar