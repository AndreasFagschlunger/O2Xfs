O2XfsWIN32:
	g++ -mno-cygwin -I"/cygdrive/c/Programme/Java/jdk1.6.0_21/include" -I"/cygdrive/c/Programme/Java/jdk1.6.0_21/include/win32" -Wl,--add-stdcall-alias -shared -o "${basedir}/O2XfsWIN32.dll" "${basedir}/native/O2XfsWIN32/O2XfsWIN32.cpp"
	
	