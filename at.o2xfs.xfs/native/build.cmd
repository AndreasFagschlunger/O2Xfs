@ECHO ON
IF NOT "%VS90COMNTOOLS%" == "" (
	CALL "%VS90COMNTOOLS%vsvars32.bat"
) ELSE IF NOT "%VS120COMNTOOLS%" == "" (
	CALL "%VS120COMNTOOLS%vsvars32.bat"
)
nmake -f make_win32.mak