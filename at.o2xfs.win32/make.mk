O2XfsWIN32:
	i686-pc-mingw32-g++ -I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/win32" -I"${SRC_DIR}" -Wl,--add-stdcall-alias -static-libgcc -static-libstdc++ -shared -o "${BIN_DIR}/O2XfsWIN32.dll" "${SRC_DIR}/O2XfsWIN32.cpp"
	
	