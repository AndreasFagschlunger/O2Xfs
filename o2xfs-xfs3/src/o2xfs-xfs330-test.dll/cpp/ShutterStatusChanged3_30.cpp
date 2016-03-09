#include "at_o2xfs_xfs_cdm_v3_30_ShutterStatusChanged3_30Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMSHUTTERSTATUSCHANGED ShutterStatusChanged;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_130_ShutterStatusChanged3_130Test_buildShutterStatusChanged3_130(JNIEnv *env, jobject obj) {
	ShutterStatusChanged.fwPosition = WFS_CDM_POSFRONT;
	ShutterStatusChanged.fwShutter = WFS_CDM_SHTJAMMED;
	return NewBuffer(env, &ShutterStatusChanged, sizeof(WFSCDMSHUTTERSTATUSCHANGED));
}