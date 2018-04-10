#include "ptr/at_o2xfs_xfs_v3_00_ptr_FormHeader3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSFRMHEADER Header;
static LPSTR FormName = "XFSFORM";
static LPSTR Fields = "Field 1\0Field 2\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_FormHeader3Test_buildFormHeader3(JNIEnv *env, jobject obj) {
	Header.lpszFormName = FormName;
	Header.wBase = WFS_FRM_MM;
	Header.wUnitX = 10;
	Header.wUnitY = 10;
	Header.wWidth = 1024;
	Header.wHeight = 768;
	Header.wAlignment = WFS_FRM_TOPLEFT;
	Header.wOrientation = WFS_FRM_LANDSCAPE;
	Header.wOffsetX = 0;
	Header.wOffsetY = 0;
	Header.wVersionMajor = 1;
	Header.wVersionMinor = 2;
	Header.lpszUserPrompt = NULL;
	Header.fwCharSupport = WFS_PTR_ASCII;
	Header.lpszFields = Fields;
	return NewBuffer(env, &Header, sizeof(WFSFRMHEADER));
}
