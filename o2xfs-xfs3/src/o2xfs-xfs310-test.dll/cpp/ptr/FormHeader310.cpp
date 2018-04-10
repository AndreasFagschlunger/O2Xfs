#include "ptr/at_o2xfs_xfs_v3_10_ptr_FormHeader310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSFRMHEADER Header;
static LPSTR FormName = "XFSFORM";
static LPSTR UserPrompt = "UserPrompt";
static LPSTR Fields = "Field 1\0Field 2\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_FormHeader310Test_buildFormHeader310(JNIEnv *env, jobject obj) {
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
	Header.lpszUserPrompt = UserPrompt;
	Header.fwCharSupport = WFS_PTR_UNICODE;
	Header.lpszFields = Fields;
	Header.wLanguageID = MAKELANGID(LANG_GERMAN, SUBLANG_DEFAULT);
	return NewBuffer(env, &Header, sizeof(WFSFRMHEADER));
}
