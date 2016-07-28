#include "cim/at_o2xfs_xfs_cim_v3_30_Inpos3_30Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMINPOS Position;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_130_Inpos3_130Test_buildInpos3_130(JNIEnv *env, jobject obj) {
	Position.fwPosition = WFS_CIM_POSINFRONT;
	Position.fwShutter = WFS_CIM_SHTCLOSED;
	Position.fwPositionStatus = WFS_CIM_PSEMPTY;
	Position.fwTransport = WFS_CIM_TPOK;
	Position.fwTransportStatus = WFS_CIM_TPSTATEMPTY;
	Position.fwJammedShutterPosition = WFS_CIM_SHUTTERPOS_NOTJAMMED;
	return NewBuffer(env, &Position, sizeof(WFSCIMINPOS));
}
