!include <win32.mak>

maj_ver=0
min_ver=0
comma_ver=0,0,0,0

CFLAGS = /c /MD /Fo"$(OUTPUT_DIR)\\" /I"$(OUTPUT_DIR)\include" /I"$(JAVA_HOME)\include" /I"$(JAVA_HOME)\include\win32" /I"$(XFS_SDK)\INCLUDE"
RCFLAGS = -DFILE_VERSION=\"$(maj_ver).$(min_ver)\" -DCOMMA_VERSION=$(comma_ver)

all: at.o2xfs.xfs at.o2xfs.xfs.conf at.o2xfs.xfs.test

at.o2xfs.xfs: at.o2xfs.xfs.obj at.o2xfs.xfs.res
	link /DLL /LIBPATH:"$(OUTPUT_DIR)\lib" /LIBPATH:"$(XFS_SDK)\LIB" /MANIFEST /MANIFESTUAC:"level='asInvoker' uiAccess='false'" "$(OUTPUT_DIR)\at.o2xfs.xfs.obj" "$(OUTPUT_DIR)\MessageHandler.obj" "$(OUTPUT_DIR)\JNIUtil.obj" "$(OUTPUT_DIR)\at.o2xfs.xfs.res" /OUT:"$(OUTPUT_DIR)\at.o2xfs.xfs.dll" Gdi32.lib User32.lib msxfs.lib at.o2xfs.win32.lib
	mt -outputresource:$(OUTPUT_DIR)\at.o2xfs.xfs.dll;#2 -manifest "$(OUTPUT_DIR)\at.o2xfs.xfs.dll.manifest"

at.o2xfs.xfs.obj:
	cl $(CFLAGS) at.o2xfs.xfs\at.o2xfs.xfs.cpp at.o2xfs.xfs\JNIUtil.cpp  at.o2xfs.xfs\MessageHandler.cpp
	
at.o2xfs.xfs.res:
	rc $(RCFLAGS) -DORG_FILENAME=\"at.o2xfs.xfs.dll\" -r -fo $(OUTPUT_DIR)\at.o2xfs.xfs.res at.o2xfs.xfs\at.o2xfs.xfs.rc
	
at.o2xfs.xfs.conf: at.o2xfs.xfs.conf.obj at.o2xfs.xfs.conf.res
	link /DLL /LIBPATH:"$(OUTPUT_DIR)\lib" /LIBPATH:"$(XFS_SDK)\LIB" /MANIFEST /MANIFESTUAC:"level='asInvoker' uiAccess='false'" "$(OUTPUT_DIR)\at.o2xfs.xfs.conf.obj" "$(OUTPUT_DIR)\at.o2xfs.xfs.conf.res" /OUT:"$(OUTPUT_DIR)\at.o2xfs.xfs.conf.dll" xfs_conf.lib at.o2xfs.win32.lib
	mt -outputresource:$(OUTPUT_DIR)\at.o2xfs.xfs.conf.dll;#2 -manifest "$(OUTPUT_DIR)\at.o2xfs.xfs.conf.dll.manifest"

at.o2xfs.xfs.conf.obj:
	cl $(CFLAGS) at.o2xfs.xfs.conf\at.o2xfs.xfs.conf.cpp
	
at.o2xfs.xfs.conf.res:
	rc $(RCFLAGS) -DORG_FILENAME=\"at.o2xfs.xfs.conf.dll\" -r -fo "$(OUTPUT_DIR)\at.o2xfs.xfs.conf.res" at.o2xfs.xfs.conf\at.o2xfs.xfs.conf.rc

at.o2xfs.xfs.test: at.o2xfs.xfs.test.obj at.o2xfs.xfs.test.res
	link /DLL /LIBPATH:"$(OUTPUT_DIR)\lib" /MANIFEST /MANIFESTUAC:"level='asInvoker' uiAccess='false'" "$(OUTPUT_DIR)\CamStructV3_20.obj" "$(OUTPUT_DIR)\at.o2xfs.xfs.test.res" /OUT:"$(OUTPUT_DIR)\at.o2xfs.xfs.test.dll" at.o2xfs.win32.lib
	mt -outputresource:$(OUTPUT_DIR)\at.o2xfs.xfs.test.dll;#2 -manifest "$(OUTPUT_DIR)\at.o2xfs.xfs.test.dll.manifest"

at.o2xfs.xfs.test.obj:
	cl $(CFLAGS) at.o2xfs.xfs.test\CamStructV3_20.cpp

at.o2xfs.xfs.test.res:
	rc $(RCFLAGS) -DORG_FILENAME=\"at.o2xfs.xfs.test.dll\" -r -fo $(OUTPUT_DIR)\at.o2xfs.xfs.test.res at.o2xfs.xfs.test\at.o2xfs.xfs.test.rc