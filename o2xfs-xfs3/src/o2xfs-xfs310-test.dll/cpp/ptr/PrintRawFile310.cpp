#include "ptr/at_o2xfs_xfs_v3_10_ptr_PrintRawFile310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRPRINTRAWFILE PrintRawFile;
static LPSTR FileName = "C:\\Temp\\FrontImage.bmp";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_PrintRawFile310Test_buildPrintRawFile310(JNIEnv *env, jobject obj) {
	PrintRawFile.lpszFileName = FileName;
	PrintRawFile.dwMediaControl = 0;
	PrintRawFile.dwPaperSource = WFS_PTR_PAPEREXTERNAL;
	return NewBuffer(env, &PrintRawFile, sizeof(WFSPTRPRINTRAWFILE));
}
