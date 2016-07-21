#include "cim/at_o2xfs_xfs_cim_v3_10_ConfigureNoteReaderOut3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMCONFIGURENOTEREADEROUT ConfigureNoteReaderOut;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_ConfigureNoteReaderOut3_110Test_buildConfigureNoteReaderOut3_110(JNIEnv *env, jobject obj) {
	ConfigureNoteReaderOut.bRebootNecessary = FALSE;
	return NewBuffer(env, &ConfigureNoteReaderOut, sizeof(WFSCIMCONFIGURENOTEREADEROUT));
}
