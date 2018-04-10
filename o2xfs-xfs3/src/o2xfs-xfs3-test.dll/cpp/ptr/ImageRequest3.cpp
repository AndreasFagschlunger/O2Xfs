#include "ptr/at_o2xfs_xfs_v3_00_ptr_ImageRequest3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRIMAGEREQUEST ImageRequest;
static LPSTR FrontImageFile = "C:\\Temp\\FrontImage.bmp";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_ImageRequest3Test_buildImageRequest3(JNIEnv *env, jobject obj) {
	ImageRequest.wFrontImageType = WFS_PTR_IMAGETIF;
	ImageRequest.wBackImageType = 0;
	ImageRequest.wFrontImageColorFormat = WFS_PTR_IMAGECOLORBINARY;
	ImageRequest.wBackImageColorFormat = 0;
	ImageRequest.wCodelineFormat = WFS_PTR_CODELINEOCR;
	ImageRequest.fwImageSource = WFS_PTR_IMAGEFRONT | WFS_PTR_IMAGEBACK;
	ImageRequest.lpszFrontImageFile = FrontImageFile;
	ImageRequest.lpszBackImageFile = NULL;
	return NewBuffer(env, &ImageRequest, sizeof(WFSPTRIMAGEREQUEST));
}
