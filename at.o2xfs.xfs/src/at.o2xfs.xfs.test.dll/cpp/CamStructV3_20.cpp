#include "at_o2xfs_xfs_cam_v320_CamStructV3_20Test.h"

#include <Windows.h>
#include <XFSCAM.H>
#include "at.o2xfs.win32.h"

static WFSCAMSTATUS status;
static WFSCAMCAPS caps;
static WFSCAMTAKEPICTEX takePictEx;

static LPSTR lpszExtra = "Key1=Value1\0Key2=Value2\0";
static LPSTR lpszCamData = "CamData";
static LPWSTR lpszUNICODECamData = L"CamData";
static LPSTR lpszPictureFile = "C:\\";

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvReserved) {
	return TRUE;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	return JNI_VERSION_1_6;
}

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cam_v320_CamStructV3_120Test_createCamStatusV3_120(JNIEnv *env, jobject obj) {
	status.fwDevice = WFS_CAM_DEVONLINE;
	for(int i = 0; i < WFS_CAM_CAMERAS_SIZE; i++) {
		status.fwMedia[i] = WFS_CAM_MEDIANOTSUPP;
		status.fwCameras[i] = WFS_CAM_CAMNOTSUPP;
		status.usPictures[i] = 0;
	}
	status.fwMedia[WFS_CAM_PERSON] = WFS_CAM_MEDIAOK;
	status.fwCameras[WFS_CAM_PERSON] = WFS_CAM_CAMOK;
	status.usPictures[WFS_CAM_PERSON] = 1;
	status.wAntiFraudModule = WFS_CAM_AFMOK;
	status.lpszExtra = lpszExtra;
	return NewBuffer(env, &status, sizeof(WFSCAMSTATUS));
}

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cam_v320_CamStructV3_120Test_createCamCapsV3_120(JNIEnv *env, jobject obj) {
	caps.wClass = WFS_SERVICE_CLASS_CAM;
	caps.fwType = WFS_CAM_TYPE_CAM;
	for(int i = 0; i < WFS_CAM_CAMERAS_SIZE; i++) {
		caps.fwCameras[i] = WFS_CAM_NOT_AVAILABLE;
	}
	caps.fwCameras[WFS_CAM_ROOM] = WFS_CAM_AVAILABLE;
	caps.usMaxPictures = 100;
	caps.fwCamData = WFS_CAM_AUTOADD | WFS_CAM_MANADD;
	caps.usMaxDataLength = 64;
	caps.fwCharSupport = WFS_CAM_ASCII | WFS_CAM_UNICODE;
	caps.lpszExtra = lpszExtra;
	caps.bPictureFile = TRUE;
	caps.bAntiFraudModule = TRUE;
	return NewBuffer(env, &caps, sizeof(WFSCAMCAPS));
}

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cam_v320_CamStructV3_120Test_createTakepictEx(JNIEnv *env, jobject obj) {
	takePictEx.wCamera = WFS_CAM_PERSON;
	takePictEx.lpszCamData = lpszCamData;
	takePictEx.lpszUNICODECamData = lpszUNICODECamData;
	takePictEx.lpszPictureFile = lpszPictureFile;
	return NewBuffer(env, &takePictEx, sizeof(WFSCAMTAKEPICTEX));
}