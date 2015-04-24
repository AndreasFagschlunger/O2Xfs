!include <win32.mak>

maj_ver=0
min_ver=0
comma_ver=0,0,0,0

CFLAGS = /c /MD /Fo"$(OUTPUT_DIR)\\" /I"$(JAVA_HOME)\include" /I"$(JAVA_HOME)\include\win32"
RCFLAGS = -DFILE_VERSION=\"$(maj_ver).$(min_ver)\" -DCOMMA_VERSION=$(comma_ver)

all: at.o2xfs.win32

at.o2xfs.win32: at.o2xfs.win32.obj at.o2xfs.win32.res
	link /DLL /MANIFEST /MANIFESTUAC:"level='asInvoker' uiAccess='false'" $(OUTPUT_DIR)\at.o2xfs.win32.obj $(OUTPUT_DIR)\at.o2xfs.win32.res /OUT:$(OUTPUT_DIR)\at.o2xfs.win32.dll
	mt -outputresource:$(OUTPUT_DIR)\at.o2xfs.win32.dll;#2 -manifest $(OUTPUT_DIR)\at.o2xfs.win32.dll.manifest

at.o2xfs.win32.obj:
	cl $(CFLAGS) at.o2xfs.win32\at.o2xfs.win32.cpp
	
at.o2xfs.win32.res:
	rc $(RCFLAGS) -DORG_FILENAME=\"at.o2xfs.win32.dll\" -r -fo $(OUTPUT_DIR)\at.o2xfs.win32.res at.o2xfs.win32\at.o2xfs.win32.rc