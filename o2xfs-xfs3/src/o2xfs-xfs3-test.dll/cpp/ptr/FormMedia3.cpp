#include "ptr/at_o2xfs_xfs_v3_00_ptr_FormMedia3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSFRMMEDIA Media;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_FormMedia3Test_buildFormMedia3(JNIEnv *env, jobject obj) {
	Media.fwMediaType = WFS_FRM_MEDIAGENERIC;
	Media.wBase = WFS_FRM_INCH;
	Media.wUnitX = 16;
	Media.wUnitY = 32;
	Media.wSizeWidth = 1024;
	Media.wSizeHeight = 768;
	Media.wPageCount = 0;
	Media.wLineCount = 0;
	Media.wPrintAreaX = 100;
	Media.wPrintAreaY = 100;
	Media.wPrintAreaWidth = 924;
	Media.wPrintAreaHeight = 686;
	Media.wRestrictedAreaX = 0;
	Media.wRestrictedAreaY = 0;
	Media.wRestrictedAreaWidth = 0;
	Media.wRestrictedAreaHeight = 0;
	Media.wStagger = 0;
	Media.wFoldType = WFS_FRM_FOLDNONE;
	Media.wPaperSources = WFS_PTR_PAPERANY;
	return NewBuffer(env, &Media, sizeof(WFSFRMMEDIA));
}
