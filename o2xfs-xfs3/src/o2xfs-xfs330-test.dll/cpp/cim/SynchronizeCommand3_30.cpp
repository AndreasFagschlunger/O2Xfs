#include "cim/at_o2xfs_xfs_cim_v3_30_SynchronizeCommand3_30Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMSYNCHRONIZECOMMAND SynchronizeCommand;
static WFSCIMRETRACT Retract;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_130_SynchronizeCommand3_130Test_buildSynchronizeCommand3_130(JNIEnv *env, jobject obj) {
	SynchronizeCommand.dwCommand = WFS_CMD_CIM_RETRACT;
	SynchronizeCommand.lpCmdData = &Retract;
	Retract.fwOutputPosition = WFS_CIM_POSOUTFRONT;
	Retract.usRetractArea = WFS_CIM_RA_TRANSPORT;
	Retract.usIndex = 0;
	return NewBuffer(env, &SynchronizeCommand, sizeof(WFSCIMSYNCHRONIZECOMMAND));
}