#include "ptr/at_o2xfs_xfs_v3_00_ptr_Image3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRIMAGE Image;
static BYTE Data[] = { 0xCA, 0xFE, 0xBA, 0xBE };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_Image3Test_buildImage3(JNIEnv *env, jobject obj) {
	Image.wImageSource = WFS_PTR_IMAGEFRONT;
	Image.wStatus = WFS_PTR_DATAOK;
	Image.ulDataLength = sizeof(Data) / sizeof(*Data);
	Image.lpbData = Data;
	return NewBuffer(env, &Image, sizeof(WFSPTRIMAGE));
}
