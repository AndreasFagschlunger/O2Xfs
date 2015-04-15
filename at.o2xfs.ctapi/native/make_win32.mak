!include <win32.mak>

maj_ver=0
min_ver=0
comma_ver=0,0,0,0

CFLAGS = /c /MD /Fo"$(OUTPUT_DIR)\\" /I"$(OUTPUT_DIR)\include" /I"$(JAVA_HOME)\include" /I"$(JAVA_HOME)\include\win32"
RCFLAGS = -DFILE_VERSION=\"$(maj_ver).$(min_ver)\" -DCOMMA_VERSION=$(comma_ver)

all: at.o2xfs.ctapi

at.o2xfs.ctapi: at.o2xfs.ctapi.obj at.o2xfs.ctapi.res
	link /DLL /LIBPATH:"$(OUTPUT_DIR)\lib" /MANIFEST /MANIFESTUAC:"level='asInvoker' uiAccess='false'" "$(OUTPUT_DIR)\at.o2xfs.ctapi.obj" "$(OUTPUT_DIR)\at.o2xfs.ctapi.res" /OUT:"$(OUTPUT_DIR)\at.o2xfs.ctapi.dll" at.o2xfs.win32.lib
	mt -outputresource:$(OUTPUT_DIR)\at.o2xfs.ctapi.dll;#2 -manifest "$(OUTPUT_DIR)\at.o2xfs.ctapi.dll.manifest"

at.o2xfs.ctapi.obj:
	cl $(CFLAGS) at.o2xfs.ctapi\at.o2xfs.ctapi.cpp
	
at.o2xfs.ctapi.res:
	rc $(RCFLAGS) -DORG_FILENAME=\"at.o2xfs.ctapi.dll\" -r -fo $(OUTPUT_DIR)\at.o2xfs.ctapi.res at.o2xfs.ctapi\at.o2xfs.ctapi.rc