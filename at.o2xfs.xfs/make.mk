all: O2Xfs O2XfsConf 

O2Xfs:
	i686-pc-mingw32-g++ -I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/win32" -I"${XFS_SDK}/INCLUDE" -I"${O2XFS_WIN32}" -I"${BUILD_DIR}" -mwindows -Wl,--add-stdcall-alias -static-libgcc -static-libstdc++ -shared -o "${BIN_DIR}/O2Xfs.dll" "${SRC_DIR}/O2Xfs/O2Xfs.cpp" "${SRC_DIR}/O2Xfs/JNIUtil.cpp" "${SRC_DIR}/O2Xfs/MessageHandler.cpp" "${BIN_DIR}/O2XfsWIN32.dll" "${XFS_SDK}/LIB/msxfs.lib"

O2XfsConf:
	i686-pc-mingw32-g++ -I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/win32" -I"${XFS_SDK}/INCLUDE" -I"${O2XFS_WIN32}" -I"${BUILD_DIR}" -Wl,--add-stdcall-alias -static-libgcc -static-libstdc++ -shared -o "${BIN_DIR}/O2XfsConf.dll" "${SRC_DIR}/O2XfsConf/O2XfsConf.cpp" "${XFS_SDK}/LIB/xfs_conf.lib"	