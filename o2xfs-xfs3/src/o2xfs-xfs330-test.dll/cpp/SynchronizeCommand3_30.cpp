#include "at_o2xfs_xfs_cdm_v3_30_SynchronizeCommand3_30Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMSYNCHRONIZECOMMAND SynchronizeCommand;
static WFSCDMRETRACT CmdData;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_130_SynchronizeCommand3_130Test_buildSynchronizeCommand3_130(JNIEnv *env, jobject obj) {
	SynchronizeCommand.dwCommand = WFS_CMD_CDM_RETRACT;
	CmdData.fwOutputPosition = WFS_CDM_POSFRONT;
	CmdData.usRetractArea = WFS_CDM_RA_RETRACT;
	CmdData.usRetractArea = 1;

	SynchronizeCommand.lpCmdData = &CmdData;
	return NewBuffer(env, &SynchronizeCommand, sizeof(WFSCDMSYNCHRONIZECOMMAND));
}