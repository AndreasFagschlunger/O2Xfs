#include "cim/at_o2xfs_xfs_cim_v3_10_ConfigureNoteReader3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCONFIGURENOTEREADER ConfigureNoteReader;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_ConfigureNoteReader3_110Test_buildConfigureNoteReader3_110(JNIEnv *env, jobject obj) {
	ConfigureNoteReader.bLoadAlways = FALSE;
	return NewBuffer(env, &ConfigureNoteReader, sizeof(WFSCIMCONFIGURENOTEREADER));
}
