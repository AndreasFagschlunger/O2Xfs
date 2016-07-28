#include "cim/at_o2xfs_xfs_cim_v3_30_ShutterStatusChanged3_30Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMSHUTTERSTATUSCHANGED ShutterStatusChanged;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_130_ShutterStatusChanged3_130Test_buildShutterStatusChanged3_130(JNIEnv *env, jobject obj) {
	ShutterStatusChanged.fwPosition = WFS_CIM_POSOUTFRONT;
	ShutterStatusChanged.fwShutter = WFS_CIM_SHTJAMMED;
	return NewBuffer(env, &ShutterStatusChanged, sizeof(WFSCIMSHUTTERSTATUSCHANGED));
}
