all: O2Xfs O2XfsConf

O2Xfs:
	g++ -mno-cygwin -I"/cygdrive/c/Program Files/XFS" -I"/cygdrive/c/Programme/Java/jdk1.6.0_21/include" -I"/cygdrive/c/Programme/Java/jdk1.6.0_21/include/win32" -Wl,--add-stdcall-alias -L"/cygdrive/c/Program Files/XFS" -shared -o "${basedir}/O2Xfs.dll" "${basedir}/native/O2Xfs/O2Xfs/O2Xfs.cpp" "${basedir}/native/O2Xfs/O2Xfs/JNIUtil.cpp" "${basedir}/native/O2Xfs/O2Xfs/MessageHandler.cpp" -lGdi32 -lmsxfs
	
O2XfsConf:
	g++ -mno-cygwin -I"/cygdrive/c/Program Files/XFS" -I"/cygdrive/c/Programme/Java/jdk1.6.0_21/include" -I"/cygdrive/c/Programme/Java/jdk1.6.0_21/include/win32" -Wl,--add-stdcall-alias -L"/cygdrive/c/Program Files/XFS" -shared -o "${basedir}/O2XfsConf.dll" "${basedir}/native/O2Xfs/O2XfsConf/O2XfsConf.cpp" -lxfs_conf	
	